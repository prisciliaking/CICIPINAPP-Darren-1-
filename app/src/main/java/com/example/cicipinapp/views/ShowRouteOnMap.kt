//package com.example.cicipinapp.views
//
//import android.graphics.Color
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.LaunchedEffect
//import androidx.compose.runtime.mutableStateListOf
//import androidx.compose.runtime.remember
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.platform.LocalContext
//import androidx.compose.ui.viewinterop.AndroidView
//import com.example.cicipinapp.service.RetrofitClient
//import org.osmdroid.tileprovider.tilesource.TileSourceFactory
//import org.osmdroid.util.GeoPoint
//import org.osmdroid.views.MapView
//import org.osmdroid.views.overlay.Polyline
//
//@Composable
//fun ShowRouteOnMap() {
//    val context = LocalContext.current
//    val mapView = remember {
//        MapView(context).apply {
//            setTileSource(TileSourceFactory.MAPNIK)
//            setMultiTouchControls(true)
//        }
//    }
//
//    val routeCoordinates = remember { mutableStateListOf<GeoPoint>() }
//
//    LaunchedEffect(Unit) {
//        // Ambil data rute
//        val response = RetrofitClient.instance.getDirections(
//            apiKey = "YOUR_API_KEY",
//            start = "106.816666,-6.200000", // Jakarta
//            end = "107.619122,-6.917464" // Bandung
//        )
//
//        // Tambahkan koordinat rute ke GeoPoint
//        response.features.firstOrNull()?.geometry?.coordinates?.forEach { coord ->
//            routeCoordinates.add(GeoPoint(coord[1], coord[0]))
//        }
//
//        // Tambahkan rute ke peta
//        val line = Polyline().apply {
//            setPoints(routeCoordinates)
//            color = Color.RED
//        }
//        mapView.overlayManager.add(line)
//        mapView.invalidate()
//    }
//
//    AndroidView(
//        factory = { mapView },
//        modifier = Modifier.fillMaxSize(),
//        update = {
//            it.controller.setZoom(10.0)
//            it.controller.setCenter(routeCoordinates.firstOrNull() ?: GeoPoint(-6.200000, 106.816666))
//        }
//    )
//}
