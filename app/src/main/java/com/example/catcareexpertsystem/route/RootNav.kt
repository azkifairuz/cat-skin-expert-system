package com.example.catcareexpertsystem.route

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.compose.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.example.catcareexpertsystem.home.HomeScreen
import com.example.catcareexpertsystem.penyakit.ListPenyakitScreen
import com.example.catcareexpertsystem.penyakit.PenyakitViewModel

@Composable
fun RootNav(
    navController: NavHostController,
    penyakitViewModel: PenyakitViewModel
){
    val listPenyakit by penyakitViewModel.data.collectAsState()
    NavHost(navController = navController, route = Graph.ROOT, startDestination = Graph.HOME){
        composable(Graph.HOME){
            HomeScreen(navController)
        }
        composable(Graph.SCREEN_PENYAKIT){
            ListPenyakitScreen(listPenyakit = listPenyakit)
        }
    }

}

object Graph {
    const val HOME = "route_home"
    const val ROOT = "root_graph"
    const val SCREEN_PENYAKIT = "root_penyakit"
    const val SCREEN_HISTORY = "root_history"


}