package com.example.catcareexpertsystem.diagnose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.catcareexpertsystem.ui.theme.ButtonPrimary
import com.example.catcareexpertsystem.ui.theme.CatcareexpertsystemTheme
import com.example.catcareexpertsystem.ui.theme.Primary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InputpetNameScreen() {
    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarColors(
                    containerColor = Primary,
                    scrolledContainerColor = Primary,
                    navigationIconContentColor = Color.White,
                    titleContentColor = Color.White,
                    actionIconContentColor = Color.White
                ),
                navigationIcon = {
                    Icon(
                        modifier = Modifier
                            .width(32.dp)
                            .height(32.dp),
                        imageVector = Icons.Filled.ArrowBack, contentDescription = "back button"
                    )
                },
                title = {
                    Text(
                        text = "Riwayat Diagnosa",
                        style = MaterialTheme.typography.headlineMedium.copy(color = Color.White),
                    )
                })
        }
    ) {
        Box(
            Modifier
                .padding(it)
                .fillMaxSize()
                .background(Primary)
        ) {
            Card(
                colors = CardColors(
                    containerColor = Color.White,
                    contentColor = Color.Black,
                    disabledContentColor = Color.Black,
                    disabledContainerColor = Color.White
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .align(Alignment.Center)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    var text by remember { mutableStateOf("") }
                    Text(
                        text = "Form Nama Hewan",
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.headlineSmall
                    )
                    Spacer(modifier = Modifier.height(16.dp))

                    OutlinedTextField(
                        modifier = Modifier.fillMaxWidth(),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedBorderColor = Primary,
                            unfocusedBorderColor = Primary,
                            focusedTextColor = Primary,
                            focusedLabelColor = Primary,
                        ),
                        value = text,
                        onValueChange = { text = it },
                        label = { Text("Nama Hewan Anda") }
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(onClick = { /*TODO*/ }, colors = ButtonDefaults.buttonColors( ButtonPrimary), modifier = Modifier.align(Alignment.End)) {
                        Text(text = "Lanjut", color = Color.White)
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun InputFormPet() {
    CatcareexpertsystemTheme {
        InputpetNameScreen()
    }

}