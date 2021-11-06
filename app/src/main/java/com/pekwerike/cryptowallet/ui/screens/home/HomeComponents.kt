package com.pekwerike.cryptowallet.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountBalanceWallet
import androidx.compose.material.icons.outlined.Equalizer
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.rounded.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.google.accompanist.drawablepainter.rememberDrawablePainter
import com.pekwerike.cryptowallet.R
import com.pekwerike.cryptowallet.ui.screens.home.NavigationItem
import com.pekwerike.cryptowallet.ui.theme.CryptoWalletTheme

private val bottomNavigationItems = listOf<NavigationItem>(
    NavigationItem(
        label = R.string.home,
        icon = Icons.Outlined.Home
    ),
    NavigationItem(
        label = R.string.wallet,
        icon = Icons.Outlined.AccountBalanceWallet
    ),
    NavigationItem(
        label = R.string.history,
        icon = Icons.Rounded.History
    ),
    NavigationItem(
        label = R.string.market,
        icon = Icons.Outlined.Equalizer
    )
)


@Composable
fun CryptoWalletBottomBar() {
    BottomNavigation(
        elevation = 12.dp,
        backgroundColor = MaterialTheme.colors.background
    ) {
        bottomNavigationItems.forEachIndexed { index, navigationItem ->
            CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                BottomNavigationItem(selected = false, onClick = { },
                    label = {
                        Text(text = stringResource(id = navigationItem.label))
                    }, icon = {
                        Icon(
                            imageVector = navigationItem.icon,
                            contentDescription = stringResource(
                                id = navigationItem.label
                            )
                        )
                    })
                if (index == 1) {
                    Spacer(modifier = Modifier.width(70.dp))
                }
            }
        }
    }
}

@Composable
fun HomeTopAppBar(
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp),
        color = MaterialTheme.colors.primary
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            val context = LocalContext.current
            //Profile picture
            Image(
                modifier = Modifier
                    .padding(start = 8.dp)
                    .clip(MaterialTheme.shapes.medium)
                    .background(color = Color.White)
                    .size(35.dp)
                    .padding(3.dp),
                painter = rememberDrawablePainter(
                    drawable =
                    ContextCompat.getDrawable(context, R.drawable.memoji)
                ),
                contentScale = ContentScale.Crop,
                contentDescription = stringResource(R.string.profile_picture)
            )

            // Title
            CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                Text(
                    modifier = Modifier.padding(horizontal = 8.dp),
                    text = "Crypto wallet",
                    style = MaterialTheme.typography.h6
                )
            }

            // Actions
            IconButton(modifier = Modifier.padding(end = 8.dp), onClick = {}) {
                Box {
                    Icon(
                        imageVector = Icons.Rounded.Notifications,
                        contentDescription = "notification"
                    )
                    Icon(
                        modifier = Modifier.size(13.5.dp).align(Alignment.TopEnd),
                        imageVector = Icons.Rounded.Circle,
                        contentDescription = "",
                        tint = Color(0xFFef5350)
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun HomeTopAppBarPreview() {
    CryptoWalletTheme {
        Surface {
            HomeTopAppBar()
        }
    }
}

@Preview
@Composable
fun CryptoWalletBottomBarPreview() {
    CryptoWalletTheme {
        Surface {
            CryptoWalletBottomBar()
        }
    }
}