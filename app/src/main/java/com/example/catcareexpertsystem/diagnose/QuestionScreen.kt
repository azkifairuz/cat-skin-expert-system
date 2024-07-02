package com.example.catcareexpertsystem.diagnose

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.catcareexpertsystem.penyakit.Penyakit
import com.example.catcareexpertsystem.ui.theme.CatcareexpertsystemTheme
import com.example.catcareexpertsystem.ui.theme.Primary

@Composable
fun QuestionScreen() {
    val viewmodel: DiagnoseViewmodel = viewModel()
    val question = viewmodel.quetion.collectAsState().value
    val currentQuestionIndex = viewmodel.currentQuestionIndex.collectAsState().value
    val currentQuestion = question.getOrNull(currentQuestionIndex)

    val answers = viewmodel.answer.collectAsState().value

    Box(modifier = Modifier
        .fillMaxSize()
        .background(Primary)
        .padding(16.dp)
    ) {
        currentQuestion?.let {
            QuestionCard(
                question = it,
                onYesClick = { viewmodel.answerQuestion(it.penyakitCode, 0.6) },
                onNoClick = { viewmodel.answerQuestion(it.penyakitCode, cf = 0.4) }
            )
        }
        if (currentQuestionIndex  > question.size -1 ) {

                AnswerHistory(answers = answers)
        }
    }

}
@Composable
fun AnswerHistory(answers: List<Answer>) {
    Column {
        Text(
            text = "Riwayat Jawaban:",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        answers.forEach { answer ->
            Text(
                text = "Pertanyaan: ${answer.penyakitCode}, Nilai: ${answer.cf}",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }
    }
}
@Composable
fun QuestionCard(question: Penyakit, onYesClick: () -> Unit, onNoClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),

        ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = question.penyakit, style = MaterialTheme.typography.headlineSmall)
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(onClick = onYesClick) {
                    Text(text = "Yes (0.6)")
                }
                Button(onClick = onNoClick) {
                    Text(text = "No (0.4)")
                }
            }
        }
    }
}


@Preview()
@Composable
fun PreviewQuestionView() {
    CatcareexpertsystemTheme {
        QuestionScreen()
    }
}