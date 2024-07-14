package com.example.goatexpertsystem.diagnose

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.goatexpertsystem.route.Graph
import com.example.goatexpertsystem.ui.theme.CatcareexpertsystemTheme
import com.example.goatexpertsystem.ui.theme.Primary

@Composable
fun QuestionScreen(navController: NavHostController) {
    val viewmodel: DiagnoseViewmodel = viewModel()
    val question = viewmodel.quetion.collectAsState().value
    val currentQuestionIndex = viewmodel.currentQuestionIndex.collectAsState().value
    val currentQuestion = question.getOrNull(currentQuestionIndex)
    val isFinish = viewmodel.isFinish.collectAsState().value
    val results = viewmodel.results.collectAsState().value

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Primary)
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        LazyColumn {
            item {
                currentQuestion?.let {
                    QuestionCard(
                        question = it,
                        onAnswerClick = { cf -> viewmodel.answerQuestion(it.gejalaCode, cf) }
                    )
                }
                if (currentQuestionIndex > question.size - 1 && isFinish) {
                    ResultScreen(
                        results = results,
                        viewModel = viewmodel,
                        navController = navController
                    )
                }
            }
        }
    }
}

@Composable
fun ResultScreen(
    results: List<Result>,
    viewModel: DiagnoseViewmodel,
    navController: NavHostController
) {
    val context = LocalContext.current
    val sharedPreferences = context.getSharedPreferences("PetPreferences", Context.MODE_PRIVATE)
    val petName = sharedPreferences.getString("temp_pet_name", "Unknown") ?: "Unknown"

    val highestResult = results.maxByOrNull { it.cf }
    val solutions = viewModel.showSolution()

    Column(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Hasil Diagnosa",
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                ),
                modifier = Modifier.padding(bottom = 16.dp)
            )

            results.forEach { result ->
                Card(
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .padding(16.dp)
                    ) {
                        Text(
                            text = result.penyakit,
                            style = MaterialTheme.typography.headlineMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Tingkat Kepercayaan: ${"%.2f".format(result.cf)}%",
                            style = MaterialTheme.typography.bodyMedium.copy(
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            highestResult?.let {
                if (it.cf > 0) {
                    Text(
                        text = "Kesimpulan: ${it.penyakit} dengan tingkat kepercayaan ${
                            "%.2f".format(
                                it.cf
                            )
                        }%",
                        style = MaterialTheme.typography.headlineSmall.copy(
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        ),
                        modifier = Modifier.padding(bottom = 16.dp)
                    )

                    Card(
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .background(MaterialTheme.colorScheme.surface)
                                .padding(16.dp)
                        ) {
                            Text(
                                text = "Solusi:",
                                style = MaterialTheme.typography.headlineSmall.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onSurface
                                ),
                                modifier = Modifier.padding(bottom = 8.dp)
                            )

                                solutions.forEach{ solution ->
                                    Text(
                                        text = solution.solution,
                                        style = MaterialTheme.typography.bodyLarge.copy(
                                            color = MaterialTheme.colorScheme.onSurface
                                        ),
                                        modifier = Modifier.padding(bottom = 8.dp)
                                    )
                                }
                        }
                    }

                } else {
                    Text(
                        text = "Kesimpulan: kambing anda tidak sakit",
                        style = MaterialTheme.typography.headlineSmall.copy(
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        ),
                        modifier = Modifier.padding(bottom = 16.dp)
                    )
                }
            }
        }

        Button(
            onClick = {
                viewModel.saveDiagnoseResult(context, petName)
                navController.navigate(Graph.SCREEN_HISTORY)
            },
            shape = RoundedCornerShape(12.dp),

            modifier = Modifier.align(Alignment.End)
        ) {
            Text(
                text = "Simpan",

                )
        }
    }
}


@Composable
fun QuestionCard(question: Gejala, onAnswerClick: (Double) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = question.gejalaName, style = MaterialTheme.typography.headlineSmall)
            Spacer(modifier = Modifier.height(8.dp))
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                AnswerButton(answer = "Pasti", cf = 1.0, onClick = onAnswerClick)
                AnswerButton(answer = "Hampir Pasti", cf = 0.8, onClick = onAnswerClick)
                AnswerButton(answer = "Kemungkinan Besar", cf = 0.6, onClick = onAnswerClick)
                AnswerButton(answer = "Mungkin", cf = 0.4, onClick = onAnswerClick)
                AnswerButton(answer = "Tidak Tahu", cf = 0.2, onClick = onAnswerClick)
                AnswerButton(answer = "Tidak", cf = 0.0, onClick = onAnswerClick)
            }
        }
    }
}

@Composable
fun AnswerButton(answer: String, cf: Double, onClick: (Double) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
    ) {
        Button(onClick = { onClick(cf) }, modifier = Modifier.fillMaxWidth()) {
            Text(text = "$answer ($cf)")
        }
    }
}


@Preview()
@Composable
fun PreviewQuestionView() {
    CatcareexpertsystemTheme {
        QuestionScreen(rememberNavController())
    }
}