package io.hussienfahmy.mefab_compose.sample

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import io.hussienfahmy.mefab_compose.CentralFab
import io.hussienfahmy.mefab_compose.EdgeFab
import io.hussienfahmy.mefab_compose.MeFab
import io.hussienfahmy.mefab_compose.rememberMeFabState
import io.hussienfahmy.mefab_compose.sample.ui.theme.SampleComposeTheme
import kotlin.math.roundToInt

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SampleComposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    var numberOfEdges by remember { mutableStateOf(1f) }
                    var closeAfterEdgeClick by remember { mutableStateOf(true) }

                    val context = LocalContext.current
                    val toast = Toast.makeText(context, "", Toast.LENGTH_SHORT)

                    var state by rememberMeFabState()

                    Scaffold(
                        floatingActionButton = {
                            MeFab(
                                state = state,
                                centralFab = {
                                    CentralFab(state = state, onClick = {
                                        state = state.inverse()
                                        with(toast) {
                                            cancel()
                                            setText("Center Clicked")
                                            show()
                                        }
                                    }) {
                                        Icon(Icons.Filled.Add, "Central Fab")
                                    }
                                },
                                fab1 = if (numberOfEdges >= 1) {
                                    {
                                        EdgeFab(state = state, onClick = {
                                            with(toast) {
                                                cancel()
                                                setText("Done Clicked")
                                                show()
                                            }
                                            if (closeAfterEdgeClick) {
                                                state = state.inverse()
                                            }
                                        }) {
                                            Icon(Icons.Filled.Done, "Fab 1")
                                        }
                                    }
                                } else null,
                                fab2 = if (numberOfEdges >= 2) {
                                    {
                                        EdgeFab(state = state, onClick = {
                                            with(toast) {
                                                cancel()
                                                setText("Edit Clicked")
                                                show()
                                            }
                                            if (closeAfterEdgeClick) {
                                                state = state.inverse()
                                            }
                                        }) {
                                            Icon(Icons.Filled.Edit, "Fab 2")
                                        }
                                    }
                                } else null,
                                fab3 = if (numberOfEdges >= 3) {
                                    {
                                        EdgeFab(state = state, onClick = {
                                            with(toast) {
                                                cancel()
                                                setText("Delete Clicked")
                                                show()
                                            }
                                            if (closeAfterEdgeClick) {
                                                state = state.inverse()
                                            }
                                        }) {
                                            Icon(Icons.Filled.Delete, "Fab 3")
                                        }
                                    }
                                } else null
                            )
                        }
                    ) {

                        Column(
                            Modifier
                                .padding(it)
                                .padding(10.dp)
                        ) {
                            Text(text = "Number Of Edge Fabs: ${numberOfEdges.toInt()}")
                            Slider(value = numberOfEdges, onValueChange = {
                                numberOfEdges = it.roundToInt().toFloat()
                            }, valueRange = 1f..3f)

                            Spacer(modifier = Modifier.height(10.dp))

                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Checkbox(checked = closeAfterEdgeClick, onCheckedChange = {
                                    closeAfterEdgeClick = !closeAfterEdgeClick
                                })

                                Text(text = "Close After Click", modifier = Modifier
                                    .clickable {
                                        closeAfterEdgeClick = !closeAfterEdgeClick
                                    })
                            }
                        }
                    }
                }
            }
        }
    }
}