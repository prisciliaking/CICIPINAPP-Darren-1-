package com.example.cicipinapp.views.cards

import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.launch
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker

@Composable
fun OSMMapWithCurrentLocation(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val fusedLocationClient = remember { LocationServices.getFusedLocationProviderClient(context) }
    val coroutineScope = rememberCoroutineScope()

    var currentLocation by remember { mutableStateOf<GeoPoint?>(null) }
    var mapView: MapView? by remember { mutableStateOf(null) }

    // Permission request launcher
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->
            if (isGranted) {
                coroutineScope.launch { fetchCurrentLocation(fusedLocationClient) { currentLocation = it } }
            }
        }
    )

    // Request location permission on first launch
    LaunchedEffect(Unit) {
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            permissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        } else {
            fetchCurrentLocation(fusedLocationClient) { currentLocation = it }
        }
    }

    Column(modifier = modifier.fillMaxSize()) {
        // OpenStreetMap view
        AndroidView(
            factory = {
                MapView(context).apply {
                    setTileSource(TileSourceFactory.MAPNIK)
                    setMultiTouchControls(true)
                    mapView = this
                }
            },
            modifier = Modifier.weight(1f),
            update = { map ->
                currentLocation?.let { location ->
                    // Center the map on the current location
                    map.controller.setZoom(15.0)
                    map.controller.setCenter(location)

                    // Add a marker for the current location
                    val marker = Marker(map).apply {
                        position = location
                        title = "Current Location"
                        setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
                    }
                    map.overlays.clear() // Clear previous overlays
                    map.overlays.add(marker)
                }
            }
        )
    }
}

// Function to fetch the current location
private fun fetchCurrentLocation(
    fusedLocationClient: FusedLocationProviderClient,
    onLocationRetrieved: (GeoPoint) -> Unit
) {
    if (ActivityCompat.checkSelfPermission(
            fusedLocationClient.applicationContext,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) != PackageManager.PERMISSION_GRANTED
    ) {
        return
    }

    fusedLocationClient.lastLocation.addOnSuccessListener { location ->
        if (location != null) {
            onLocationRetrieved(GeoPoint(location.latitude, location.longitude))
        }
    }.addOnFailureListener {
        it.printStackTrace()
    }
}
