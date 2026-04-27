package com.ElOuedUniv.maktaba.presentation.book

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.ElOuedUniv.maktaba.data.model.Book

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookListView(
    onCategoriesClick: () -> Unit = {},
    onAddBookClick: () -> Unit = {},
    onBookClick: (String) -> Unit = {},
    viewModel: BookViewModel = hiltViewModel()
) {

    val uiState by viewModel.uiState.collectAsState()

    val pink = Color(0xFFE91E63)
    val lightPink = Color(0xFFF8BBD0)
    val softPink = Color(0xFFFCE4EC)

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        "MY LIBRARY",
                        style = MaterialTheme.typography.headlineMedium.copy(
                            fontWeight = FontWeight.ExtraBold,
                            letterSpacing = 2.sp,
                            color = pink
                        )
                    )
                },
                actions = {
                    IconButton(onClick = { viewModel.onAction(BookUiAction.OnToggleGridColumns) }) {
                        val icon = when (uiState.gridColumns) {
                            1 -> Icons.Default.ViewAgenda
                            2 -> Icons.Default.GridView
                            else -> Icons.Default.ViewModule
                        }
                        Icon(icon, contentDescription = null, tint = pink)
                    }

                    IconButton(onClick = onCategoriesClick) {
                        Icon(Icons.Default.List, contentDescription = null, tint = pink)
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = softPink
                )
            )
        },

        floatingActionButton = {
            FloatingActionButton(
                onClick = onAddBookClick,
                containerColor = pink,
                contentColor = Color.White,
                shape = CircleShape
            ) {
                Icon(Icons.Default.Add, contentDescription = null)
            }
        }
    ) { paddingValues ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(
                    Brush.verticalGradient(
                        listOf(softPink, Color.White)
                    )
                )
        ) {

            if (uiState.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center),
                    color = pink
                )
            } else {
                if (uiState.books.isEmpty()) {
                    EmptyBooksMessage(modifier = Modifier.align(Alignment.Center))
                } else {
                    BookGrid(
                        books = uiState.books,
                        columns = uiState.gridColumns,onBookClick = onBookClick,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
        }
    }
}

@Composable
fun BookGrid(
    books: List<Book>,
    columns: Int,
    onBookClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(columns),
        modifier = modifier,
        contentPadding = PaddingValues(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(books) { book ->
            BookCard(
                book = book,
                onClick = { onBookClick(book.isbn) },
                compact = columns == 3
            )
        }
    }
}

@Composable
fun BookCard(book: Book, onClick: () -> Unit, compact: Boolean = false) {

    val pink = Color(0xFFE91E63)
    val lightPink = Color(0xFFF8BBD0)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(if (compact) 200.dp else 320.dp),
        onClick = onClick,
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(10.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {

        Column(modifier = Modifier.fillMaxSize()) {

            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .background(lightPink.copy(alpha = 0.3f))
            ) {

                if (book.imageUrl != null) {
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(book.imageUrl)
                            .crossfade(true)
                            .build(),
                        contentDescription = book.title,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                } else {
                    Icon(
                        imageVector = Icons.Default.Book,
                        contentDescription = null,
                        modifier = Modifier
                            .size(if (compact) 30.dp else 60.dp)
                            .align(Alignment.Center),
                        tint = pink.copy(alpha = 0.5f)
                    )
                }
            }

            Column(
                modifier = Modifier
                    .padding(if (compact) 8.dp else 12.dp)
                    .fillMaxWidth()
            ) {

                Text(
                    text = book.title,
                    style = if (compact)
                        MaterialTheme.typography.labelSmall
                    else
                        MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    maxLines = 2,
                    minLines = 2,
                    color = Color.Black
                )

                if (!compact) {

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.Bottom
                    ) {

                        Column {
                            Text(
                                text = "ISBN",
                                style = MaterialTheme.typography.labelSmall,
                                color = Color.Gray
                            )
                            Text(
                                text = book.isbn.take(5) + "...",
                                style = MaterialTheme.typography.bodySmall,
                                fontWeight = FontWeight.Medium
                            )
                        }

                        val statusText =
                            if (book.nbPages > 0) "Reading" else "Finished";
                        val statusIcon =
                        if (book.nbPages > 0) Icons.Default.MenuBook else Icons.Default.CheckCircle

                        val statusColor =
                            if (book.nbPages > 0) pink else Color(0xFF4CAF50)

                        Column(horizontalAlignment = Alignment.End) {

                            Text(
                                text = "Status",
                                style = MaterialTheme.typography.labelSmall,
                                color = Color.Gray
                            )

                            Row(verticalAlignment = Alignment.CenterVertically) {

                                Icon(
                                    imageVector = statusIcon,
                                    contentDescription = null,
                                    modifier = Modifier.size(14.dp),
                                    tint = statusColor
                                )

                                Spacer(modifier = Modifier.width(4.dp))

                                Text(
                                    text = statusText,
                                    style = MaterialTheme.typography.bodySmall,
                                    fontWeight = FontWeight.Bold,
                                    color = statusColor
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun EmptyBooksMessage(modifier: Modifier = Modifier) {

    val pink = Color(0xFFE91E63)

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "📚",
            style = MaterialTheme.typography.displayLarge
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "No books in your library",
            style = MaterialTheme.typography.titleMedium,
            color = pink
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Click the + button to add a new book",
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Gray
        )
    }
}