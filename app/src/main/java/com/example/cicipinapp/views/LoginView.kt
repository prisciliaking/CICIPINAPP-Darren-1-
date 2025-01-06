package com.example.cicipinapp.views

import android.content.Context
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.cicipinapp.R
import com.example.cicipinapp.navigation.Screen
import com.example.cicipinapp.viewModels.AuthenticationViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginView(
    authenticationViewModel: AuthenticationViewModel,
    modifier: Modifier = Modifier,
    navController: NavHostController,
    context: Context
){
    var textPassword by remember { mutableStateOf(authenticationViewModel.passwordInput) }
    var textUsername by remember { mutableStateOf(authenticationViewModel.usernameInput) }
    var passwordVisible by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
        ) {
            Image(
                painter = painterResource(R.drawable.cicipinlogo),
                contentDescription = "Logo",
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(19f / 4f)
                    .size(150.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))
            Column(
                modifier = Modifier
                    .padding(16.dp),
            ) {
                TextField(
                    value = textUsername,
                    onValueChange = { textUsername = it },
                    label = { Text("Enter username") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .background(Color.White, shape = RoundedCornerShape(8.dp))
                        .border(1.dp, Color.Red, shape = RoundedCornerShape(18.dp)),

                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = Color.Transparent, // Menghilangkan warna default dari TextField
                        unfocusedIndicatorColor = Color.Transparent, // Menghilangkan garis hitam saat tidak fokus
                        focusedIndicatorColor = Color.Transparent // Menghilangkan garis hitam saat fokus
                    )
                )
                Spacer(modifier = Modifier.height(16.dp))


                TextField(
                    value = textPassword,
                    onValueChange = { textPassword = it },
                    label = { Text("Enter password") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .background(Color.White, shape = RoundedCornerShape(8.dp))
                        .border(1.dp, Color.Red, shape = RoundedCornerShape(18.dp)),
                    visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    trailingIcon = {
                        val icon = if (passwordVisible) Icons.Default.KeyboardArrowDown else Icons.Default.KeyboardArrowDown
                        val description = if (passwordVisible) "Hide password" else "Show password"

                        IconButton(onClick = { passwordVisible = !passwordVisible }) {
                            Icon(imageVector = icon, contentDescription = description)
                        }
                    },
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent
                    )
                )

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = { authenticationViewModel.loginUser(navController, textUsername, textPassword) },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
                    shape = RoundedCornerShape(24.dp), // Membuat tombol dengan sudut membulat
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .height(48.dp) // Menentukan tinggi tombol
                ) {
                    Text(
                        text = "Login",
                        color = Color.White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))

        Column(
            modifier = Modifier
                .padding(bottom = 48.dp)
                .clickable { navController.navigate(Screen.Register.route)  }
            , // Memberikan ruang untuk teks di bawah
        ) {
            Text(
                text = "Didn't have any account? Sign Up",
                textAlign = TextAlign.Center,
                color = Color.Gray,
                fontSize = 14.sp
            )

        }


    }

}

@Preview(showBackground = true)
@Composable
private fun LoginPreview(){
    LoginView(modifier = Modifier
        .fillMaxSize()
        .padding(20.dp),
        authenticationViewModel = viewModel(),
        navController = rememberNavController(),
        context = LocalContext.current)
}