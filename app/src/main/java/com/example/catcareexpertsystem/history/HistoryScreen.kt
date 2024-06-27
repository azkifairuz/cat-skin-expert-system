package com.example.catcareexpertsystem.history

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.catcareexpertsystem.ui.theme.CatcareexpertsystemTheme
import com.example.catcareexpertsystem.ui.theme.Primary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoryScreen(historyList: List<DiagnosesHistory>) {
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
                        modifier = Modifier.width(32.dp).height(32.dp),
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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF1E1E2E))
                .padding(vertical = it.calculateTopPadding(), horizontal = 16.dp)
        ) {

            LazyColumn {
                items(historyList) { history ->
                    CardHistory(
                        petName = history.petName,
                        diagnoseResult = history.diagnoseResult,
                        date = history.date
                    )
                }
            }
        }
    }
}

@Composable
fun CardHistory(petName: String, diagnoseResult: String, date: String) {
    Card(
        colors = CardColors(
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = Color.Black,
            disabledContentColor = Color.Black,
            disabledContainerColor = MaterialTheme.colorScheme.surface
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = petName, style = MaterialTheme.typography.headlineSmall)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = diagnoseResult, style = MaterialTheme.typography.bodyMedium)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = date, style = MaterialTheme.typography.bodySmall)
        }
    }
}

@Preview
@Composable
fun HistoryScreenPreview() {
    CatcareexpertsystemTheme {

        val sampleHistory = listOf(
            DiagnosesHistory("Kitty", "Cacingan", "2024-06-01"),
            DiagnosesHistory("Tom", "Dermatofitosis", "2024-06-02"),
            DiagnosesHistory("Garfield", "Feline Chlamydia", "2024-06-03")
        )
        HistoryScreen(historyList = sampleHistory)
    }
}
