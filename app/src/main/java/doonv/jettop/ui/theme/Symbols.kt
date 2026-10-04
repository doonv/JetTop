package doonv.jettop.ui.theme

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

@Suppress("RedundantCompanionReference")
object Symbols {
    object Filled {
        val Home: ImageVector by lazy {
            ImageVector.Builder(
                name = "home",
                defaultWidth = 24.dp,
                defaultHeight = 24.dp,
                viewportWidth = 24f,
                viewportHeight = 24f,
            )
                .apply {
                    path(
                        fill = SolidColor(Color.Black),
                        fillAlpha = 1f,
                        stroke = null,
                        strokeAlpha = 1f,
                        strokeLineWidth = 1f,
                        strokeLineCap = StrokeCap.Butt,
                        strokeLineJoin = StrokeJoin.Bevel,
                        strokeLineMiter = 1f,
                        pathFillType = PathFillType.NonZero,
                    ) {
                        moveTo(4f, 21f)
                        verticalLineTo(9f)
                        lineTo(12f, 3f)
                        lineToRelative(8f, 6f)
                        verticalLineTo(21f)
                        horizontalLineTo(14f)
                        verticalLineTo(14f)
                        horizontalLineTo(10f)
                        verticalLineToRelative(7f)
                        horizontalLineTo(4f)
                        close()
                    }
                }
                .build()
        }

        val Mail: ImageVector by lazy {
            ImageVector.Builder(
                name = "mail",
                defaultWidth = 24.dp,
                defaultHeight = 24.dp,
                viewportWidth = 24f,
                viewportHeight = 24f,
            )
                .apply {
                    path(
                        fill = SolidColor(Color.Black),
                        fillAlpha = 1f,
                        stroke = null,
                        strokeAlpha = 1f,
                        strokeLineWidth = 1f,
                        strokeLineCap = StrokeCap.Butt,
                        strokeLineJoin = StrokeJoin.Bevel,
                        strokeLineMiter = 1f,
                        pathFillType = PathFillType.NonZero,
                    ) {
                        moveTo(4f, 20f)
                        quadTo(3.18f, 20f, 2.59f, 19.41f)
                        reflectiveQuadTo(2f, 18f)
                        verticalLineTo(6f)
                        quadTo(2f, 5.18f, 2.59f, 4.59f)
                        reflectiveQuadTo(4f, 4f)
                        horizontalLineTo(20f)
                        quadToRelative(0.83f, 0f, 1.41f, 0.59f)
                        quadTo(22f, 5.18f, 22f, 6f)
                        verticalLineTo(18f)
                        quadToRelative(0f, 0.82f, -0.59f, 1.41f)
                        reflectiveQuadTo(20f, 20f)
                        horizontalLineTo(4f)
                        close()
                        moveToRelative(8f, -7f)
                        lineTo(20f, 8f)
                        verticalLineTo(6f)
                        lineToRelative(-8f, 5f)
                        lineTo(4f, 6f)
                        verticalLineTo(8f)
                        lineToRelative(8f, 5f)
                        close()
                    }
                }
                .build()
        }

        val AccountBox: ImageVector by lazy {
            ImageVector.Builder(
                name = "account_box",
                defaultWidth = 24.dp,
                defaultHeight = 24.dp,
                viewportWidth = 24f,
                viewportHeight = 24f,
            )
                .apply {
                    path(
                        fill = SolidColor(Color.Black),
                        fillAlpha = 1f,
                        stroke = null,
                        strokeAlpha = 1f,
                        strokeLineWidth = 1f,
                        strokeLineCap = StrokeCap.Butt,
                        strokeLineJoin = StrokeJoin.Bevel,
                        strokeLineMiter = 1f,
                        pathFillType = PathFillType.NonZero,
                    ) {
                        moveTo(14.48f, 11.98f)
                        quadTo(15.5f, 10.95f, 15.5f, 9.5f)
                        reflectiveQuadTo(14.48f, 7.02f)
                        reflectiveQuadTo(12f, 6f)
                        reflectiveQuadTo(9.53f, 7.02f)
                        reflectiveQuadTo(8.5f, 9.5f)
                        reflectiveQuadToRelative(1.03f, 2.47f)
                        reflectiveQuadTo(12f, 13f)
                        reflectiveQuadToRelative(2.48f, -1.03f)
                        close()
                        moveTo(5f, 21f)
                        quadTo(4.18f, 21f, 3.59f, 20.41f)
                        reflectiveQuadTo(3f, 19f)
                        verticalLineTo(5f)
                        quadTo(3f, 4.17f, 3.59f, 3.59f)
                        reflectiveQuadTo(5f, 3f)
                        horizontalLineTo(19f)
                        quadToRelative(0.83f, 0f, 1.41f, 0.59f)
                        reflectiveQuadTo(21f, 5f)
                        verticalLineTo(19f)
                        quadToRelative(0f, 0.82f, -0.59f, 1.41f)
                        reflectiveQuadTo(19f, 21f)
                        horizontalLineTo(5f)
                        close()
                        moveTo(5f, 19f)
                        horizontalLineTo(19f)
                        quadToRelative(0f, -0.07f, 0f, -0.57f)
                        reflectiveQuadToRelative(0f, -0.57f)
                        quadTo(17.65f, 16.52f, 15.86f, 15.76f)
                        reflectiveQuadTo(12f, 15f)
                        quadTo(9.93f, 15f, 8.14f, 15.76f)
                        reflectiveQuadTo(5f, 17.85f)
                        quadToRelative(0f, 0.07f, 0f, 0.57f)
                        reflectiveQuadTo(5f, 19f)
                        close()
                    }
                }
                .build()
        }

        val Edit: ImageVector by lazy {
            ImageVector.Builder(
                name = "edit",
                defaultWidth = 24.dp,
                defaultHeight = 24.dp,
                viewportWidth = 24f,
                viewportHeight = 24f,
            )
                .apply {
                    path(
                        fill = SolidColor(Color.Black),
                        fillAlpha = 1f,
                        stroke = null,
                        strokeAlpha = 1f,
                        strokeLineWidth = 1f,
                        strokeLineCap = StrokeCap.Butt,
                        strokeLineJoin = StrokeJoin.Bevel,
                        strokeLineMiter = 1f,
                        pathFillType = PathFillType.NonZero,
                    ) {
                        moveTo(3f, 21f)
                        verticalLineTo(16.75f)
                        lineTo(16.2f, 3.57f)
                        quadTo(16.5f, 3.3f, 16.86f, 3.15f)
                        reflectiveQuadTo(17.63f, 3f)
                        quadToRelative(0.4f, 0f, 0.78f, 0.15f)
                        reflectiveQuadTo(19.05f, 3.6f)
                        lineTo(20.43f, 5f)
                        quadToRelative(0.3f, 0.27f, 0.44f, 0.65f)
                        reflectiveQuadTo(21f, 6.4f)
                        quadToRelative(0f, 0.4f, -0.14f, 0.76f)
                        reflectiveQuadTo(20.43f, 7.82f)
                        lineTo(7.25f, 21f)
                        horizontalLineTo(3f)
                        close()
                        moveTo(17.6f, 7.8f)
                        lineTo(19f, 6.4f)
                        lineTo(17.6f, 5f)
                        lineTo(16.2f, 6.4f)
                        lineToRelative(1.4f, 1.4f)
                        close()
                    }
                }
                .build()
        }

        val Download: ImageVector by lazy {
            ImageVector.Builder(
                name = "download",
                defaultWidth = 24.dp,
                defaultHeight = 24.dp,
                viewportWidth = 24f,
                viewportHeight = 24f,
            )
                .apply {
                    path(
                        fill = SolidColor(Color.Black),
                        fillAlpha = 1f,
                        stroke = null,
                        strokeAlpha = 1f,
                        strokeLineWidth = 1f,
                        strokeLineCap = StrokeCap.Butt,
                        strokeLineJoin = StrokeJoin.Bevel,
                        strokeLineMiter = 1f,
                        pathFillType = PathFillType.NonZero,
                    ) {
                        moveTo(12f, 16f)
                        lineTo(7f, 11f)
                        lineTo(8.4f, 9.55f)
                        lineToRelative(2.6f, 2.6f)
                        verticalLineTo(4f)
                        horizontalLineToRelative(2f)
                        verticalLineToRelative(8.15f)
                        lineToRelative(2.6f, -2.6f)
                        lineTo(17f, 11f)
                        lineToRelative(-5f, 5f)
                        close()
                        moveTo(6f, 20f)
                        quadTo(5.18f, 20f, 4.59f, 19.41f)
                        reflectiveQuadTo(4f, 18f)
                        verticalLineTo(15f)
                        horizontalLineTo(6f)
                        verticalLineToRelative(3f)
                        horizontalLineTo(18f)
                        verticalLineTo(15f)
                        horizontalLineToRelative(2f)
                        verticalLineToRelative(3f)
                        quadToRelative(0f, 0.82f, -0.59f, 1.41f)
                        reflectiveQuadTo(18f, 20f)
                        horizontalLineTo(6f)
                        close()
                    }
                }
                .build()
        }

        val Settings: ImageVector by lazy {
            ImageVector.Builder(
                name = "settings",
                defaultWidth = 24.dp,
                defaultHeight = 24.dp,
                viewportWidth = 24f,
                viewportHeight = 24f,
            )
                .apply {
                    path(
                        fill = SolidColor(Color.Black),
                        fillAlpha = 1f,
                        stroke = null,
                        strokeAlpha = 1f,
                        strokeLineWidth = 1f,
                        strokeLineCap = StrokeCap.Butt,
                        strokeLineJoin = StrokeJoin.Bevel,
                        strokeLineMiter = 1f,
                        pathFillType = PathFillType.NonZero,
                    ) {
                        moveTo(9.25f, 22f)
                        lineTo(8.85f, 18.8f)
                        quadTo(8.53f, 18.68f, 8.24f, 18.5f)
                        reflectiveQuadTo(7.68f, 18.13f)
                        lineTo(4.7f, 19.38f)
                        lineTo(1.95f, 14.63f)
                        lineTo(4.53f, 12.68f)
                        quadTo(4.5f, 12.5f, 4.5f, 12.34f)
                        quadToRelative(0f, -0.16f, 0f, -0.34f)
                        reflectiveQuadToRelative(0f, -0.34f)
                        reflectiveQuadTo(4.53f, 11.33f)
                        lineTo(1.95f, 9.38f)
                        lineTo(4.7f, 4.63f)
                        lineTo(7.68f, 5.88f)
                        quadTo(7.95f, 5.68f, 8.25f, 5.5f)
                        reflectiveQuadTo(8.85f, 5.2f)
                        lineTo(9.25f, 2f)
                        horizontalLineToRelative(5.5f)
                        lineToRelative(0.4f, 3.2f)
                        quadToRelative(0.33f, 0.13f, 0.61f, 0.3f)
                        reflectiveQuadToRelative(0.56f, 0.38f)
                        lineTo(19.3f, 4.63f)
                        lineToRelative(2.75f, 4.75f)
                        lineToRelative(-2.57f, 1.95f)
                        quadToRelative(0.02f, 0.18f, 0.02f, 0.34f)
                        reflectiveQuadToRelative(0f, 0.34f)
                        reflectiveQuadToRelative(0f, 0.34f)
                        reflectiveQuadToRelative(-0.05f, 0.34f)
                        lineToRelative(2.57f, 1.95f)
                        lineToRelative(-2.75f, 4.75f)
                        lineTo(16.33f, 18.13f)
                        quadToRelative(-0.27f, 0.2f, -0.57f, 0.38f)
                        reflectiveQuadToRelative(-0.6f, 0.3f)
                        lineTo(14.75f, 22f)
                        horizontalLineTo(9.25f)
                        close()
                        moveToRelative(2.8f, -6.5f)
                        quadToRelative(1.45f, 0f, 2.47f, -1.03f)
                        reflectiveQuadTo(15.55f, 12f)
                        reflectiveQuadTo(14.53f, 9.52f)
                        reflectiveQuadTo(12.05f, 8.5f)
                        quadToRelative(-1.47f, 0f, -2.49f, 1.02f)
                        reflectiveQuadTo(8.55f, 12f)
                        reflectiveQuadToRelative(1.01f, 2.47f)
                        reflectiveQuadToRelative(2.49f, 1.03f)
                        close()
                    }
                }
                .build()
        }

        val Campaign: ImageVector by lazy {
            ImageVector.Builder(
                name = "campaign",
                defaultWidth = 24.dp,
                defaultHeight = 24.dp,
                viewportWidth = 24f,
                viewportHeight = 24f,
            )
                .apply {
                    path(
                        fill = SolidColor(Color.Black),
                        fillAlpha = 1f,
                        stroke = null,
                        strokeAlpha = 1f,
                        strokeLineWidth = 1f,
                        strokeLineCap = StrokeCap.Butt,
                        strokeLineJoin = StrokeJoin.Bevel,
                        strokeLineMiter = 1f,
                        pathFillType = PathFillType.NonZero,
                    ) {
                        moveTo(18f, 13f)
                        verticalLineTo(11f)
                        horizontalLineToRelative(4f)
                        verticalLineToRelative(2f)
                        horizontalLineTo(18f)
                        close()
                        moveToRelative(1.2f, 7f)
                        lineTo(16f, 17.6f)
                        lineTo(17.2f, 16f)
                        lineToRelative(3.2f, 2.4f)
                        lineTo(19.2f, 20f)
                        close()
                        moveTo(17.2f, 8f)
                        lineTo(16f, 6.4f)
                        lineTo(19.2f, 4f)
                        lineToRelative(1.2f, 1.6f)
                        lineTo(17.2f, 8f)
                        close()
                        moveTo(5f, 19f)
                        verticalLineTo(15f)
                        horizontalLineTo(4f)
                        quadTo(3.18f, 15f, 2.59f, 14.41f)
                        reflectiveQuadTo(2f, 13f)
                        verticalLineTo(11f)
                        quadTo(2f, 10.17f, 2.59f, 9.59f)
                        reflectiveQuadTo(4f, 9f)
                        horizontalLineTo(8f)
                        lineTo(13f, 6f)
                        verticalLineTo(18f)
                        lineTo(8f, 15f)
                        horizontalLineTo(7f)
                        verticalLineToRelative(4f)
                        horizontalLineTo(5f)
                        close()
                        moveToRelative(9f, -3.65f)
                        verticalLineTo(8.65f)
                        quadToRelative(0.68f, 0.6f, 1.09f, 1.46f)
                        quadTo(15.5f, 10.98f, 15.5f, 12f)
                        quadToRelative(0f, 1.02f, -0.41f, 1.89f)
                        reflectiveQuadTo(14f, 15.35f)
                        close()
                    }
                }
                .build()
        }

        val Psychology: ImageVector by lazy {
            ImageVector.Builder(
                name = "psychology",
                defaultWidth = 24.dp,
                defaultHeight = 24.dp,
                viewportWidth = 24f,
                viewportHeight = 24f,
            )
                .apply {
                    path(
                        fill = SolidColor(Color.Black),
                        fillAlpha = 1f,
                        stroke = null,
                        strokeAlpha = 1f,
                        strokeLineWidth = 1f,
                        strokeLineCap = StrokeCap.Butt,
                        strokeLineJoin = StrokeJoin.Bevel,
                        strokeLineMiter = 1f,
                        pathFillType = PathFillType.NonZero,
                    ) {
                        moveTo(11f, 15f)
                        horizontalLineToRelative(2f)
                        lineToRelative(0.15f, -1.25f)
                        quadToRelative(0.2f, -0.08f, 0.36f, -0.18f)
                        reflectiveQuadTo(13.8f, 13.35f)
                        lineToRelative(1.15f, 0.5f)
                        lineToRelative(1f, -1.7f)
                        lineToRelative(-1f, -0.75f)
                        quadTo(15f, 11.2f, 15f, 11f)
                        reflectiveQuadTo(14.95f, 10.6f)
                        lineToRelative(1f, -0.75f)
                        lineToRelative(-1f, -1.7f)
                        lineTo(13.8f, 8.65f)
                        quadTo(13.68f, 8.52f, 13.51f, 8.42f)
                        reflectiveQuadTo(13.15f, 8.25f)
                        lineTo(13f, 7f)
                        horizontalLineTo(11f)
                        lineTo(10.85f, 8.25f)
                        quadToRelative(-0.2f, 0.07f, -0.36f, 0.17f)
                        reflectiveQuadTo(10.2f, 8.65f)
                        lineTo(9.05f, 8.15f)
                        lineToRelative(-1f, 1.7f)
                        lineToRelative(1f, 0.75f)
                        quadTo(9f, 10.8f, 9f, 11f)
                        reflectiveQuadToRelative(0.05f, 0.4f)
                        lineToRelative(-1f, 0.75f)
                        lineToRelative(1f, 1.7f)
                        lineToRelative(1.15f, -0.5f)
                        quadToRelative(0.13f, 0.13f, 0.29f, 0.22f)
                        reflectiveQuadToRelative(0.36f, 0.18f)
                        lineTo(11f, 15f)
                        close()
                        moveTo(10.94f, 12.06f)
                        quadTo(10.5f, 11.63f, 10.5f, 11f)
                        reflectiveQuadTo(10.94f, 9.94f)
                        reflectiveQuadTo(12f, 9.5f)
                        reflectiveQuadToRelative(1.06f, 0.44f)
                        reflectiveQuadTo(13.5f, 11f)
                        reflectiveQuadToRelative(-0.44f, 1.06f)
                        reflectiveQuadTo(12f, 12.5f)
                        reflectiveQuadTo(10.94f, 12.06f)
                        close()
                        moveTo(6f, 22f)
                        verticalLineTo(17.7f)
                        quadTo(4.58f, 16.4f, 3.79f, 14.66f)
                        reflectiveQuadTo(3f, 11f)
                        quadTo(3f, 7.25f, 5.63f, 4.63f)
                        reflectiveQuadTo(12f, 2f)
                        quadToRelative(3.13f, 0f, 5.54f, 1.84f)
                        quadToRelative(2.41f, 1.84f, 3.14f, 4.79f)
                        lineToRelative(1.3f, 5.13f)
                        quadToRelative(0.13f, 0.47f, -0.18f, 0.86f)
                        reflectiveQuadTo(21f, 15f)
                        horizontalLineTo(19f)
                        verticalLineToRelative(3f)
                        quadToRelative(0f, 0.82f, -0.59f, 1.41f)
                        reflectiveQuadTo(17f, 20f)
                        horizontalLineTo(15f)
                        verticalLineToRelative(2f)
                        horizontalLineTo(6f)
                        close()
                    }
                }
                .build()
        }

        val Group: ImageVector by lazy {
            ImageVector.Builder(
                name = "group",
                defaultWidth = 24.dp,
                defaultHeight = 24.dp,
                viewportWidth = 24f,
                viewportHeight = 24f,
            )
                .apply {
                    path(
                        fill = SolidColor(Color.Black),
                        fillAlpha = 1f,
                        stroke = null,
                        strokeAlpha = 1f,
                        strokeLineWidth = 1f,
                        strokeLineCap = StrokeCap.Butt,
                        strokeLineJoin = StrokeJoin.Bevel,
                        strokeLineMiter = 1f,
                        pathFillType = PathFillType.NonZero,
                    ) {
                        moveTo(1f, 20f)
                        verticalLineTo(17.2f)
                        quadTo(1f, 16.35f, 1.44f, 15.64f)
                        quadTo(1.88f, 14.93f, 2.6f, 14.55f)
                        quadTo(4.15f, 13.77f, 5.75f, 13.39f)
                        reflectiveQuadTo(9f, 13f)
                        reflectiveQuadToRelative(3.25f, 0.39f)
                        reflectiveQuadToRelative(3.15f, 1.16f)
                        quadToRelative(0.72f, 0.38f, 1.16f, 1.09f)
                        reflectiveQuadTo(17f, 17.2f)
                        verticalLineTo(20f)
                        horizontalLineTo(1f)
                        close()
                        moveToRelative(18f, 0f)
                        verticalLineTo(17f)
                        quadToRelative(0f, -1.1f, -0.61f, -2.11f)
                        quadTo(17.78f, 13.88f, 16.65f, 13.15f)
                        quadToRelative(1.27f, 0.15f, 2.4f, 0.51f)
                        quadToRelative(1.13f, 0.36f, 2.1f, 0.89f)
                        quadToRelative(0.9f, 0.5f, 1.38f, 1.11f)
                        reflectiveQuadTo(23f, 17f)
                        verticalLineToRelative(3f)
                        horizontalLineTo(19f)
                        close()
                        moveTo(6.18f, 10.83f)
                        quadTo(5f, 9.65f, 5f, 8f)
                        reflectiveQuadTo(6.18f, 5.18f)
                        reflectiveQuadTo(9f, 4f)
                        reflectiveQuadToRelative(2.83f, 1.18f)
                        reflectiveQuadTo(13f, 8f)
                        reflectiveQuadToRelative(-1.17f, 2.82f)
                        reflectiveQuadTo(9f, 12f)
                        reflectiveQuadTo(6.18f, 10.83f)
                        close()
                        moveToRelative(11.65f, 0f)
                        quadTo(16.65f, 12f, 15f, 12f)
                        quadToRelative(-0.27f, 0f, -0.7f, -0.06f)
                        reflectiveQuadTo(13.6f, 11.8f)
                        quadTo(14.28f, 11f, 14.64f, 10.02f)
                        reflectiveQuadTo(15f, 8f)
                        reflectiveQuadTo(14.64f, 5.97f)
                        reflectiveQuadTo(13.6f, 4.2f)
                        quadTo(13.95f, 4.07f, 14.3f, 4.04f)
                        reflectiveQuadTo(15f, 4f)
                        quadToRelative(1.65f, 0f, 2.82f, 1.18f)
                        reflectiveQuadTo(19f, 8f)
                        reflectiveQuadToRelative(-1.18f, 2.82f)
                        close()
                    }
                }
                .build()
        }

        val AssignmentTurnedIn: ImageVector by lazy {
            ImageVector.Builder(
                name = "assignment_turned_in",
                defaultWidth = 24.dp,
                defaultHeight = 24.dp,
                viewportWidth = 24f,
                viewportHeight = 24f,
            )
                .apply {
                    path(
                        fill = SolidColor(Color.Black),
                        fillAlpha = 1f,
                        stroke = null,
                        strokeAlpha = 1f,
                        strokeLineWidth = 1f,
                        strokeLineCap = StrokeCap.Butt,
                        strokeLineJoin = StrokeJoin.Bevel,
                        strokeLineMiter = 1f,
                        pathFillType = PathFillType.NonZero,
                    ) {
                        moveTo(10.6f, 16.05f)
                        lineTo(17.65f, 9f)
                        lineTo(16.25f, 7.6f)
                        lineTo(10.6f, 13.25f)
                        lineTo(7.75f, 10.4f)
                        lineToRelative(-1.4f, 1.4f)
                        lineToRelative(4.25f, 4.25f)
                        close()
                        moveTo(5f, 21f)
                        quadTo(4.18f, 21f, 3.59f, 20.41f)
                        reflectiveQuadTo(3f, 19f)
                        verticalLineTo(5f)
                        quadTo(3f, 4.17f, 3.59f, 3.59f)
                        reflectiveQuadTo(5f, 3f)
                        horizontalLineTo(9.2f)
                        quadTo(9.53f, 2.1f, 10.29f, 1.55f)
                        reflectiveQuadTo(12f, 1f)
                        reflectiveQuadToRelative(1.71f, 0.55f)
                        reflectiveQuadTo(14.8f, 3f)
                        horizontalLineTo(19f)
                        quadToRelative(0.83f, 0f, 1.41f, 0.59f)
                        reflectiveQuadTo(21f, 5f)
                        verticalLineTo(19f)
                        quadToRelative(0f, 0.82f, -0.59f, 1.41f)
                        reflectiveQuadTo(19f, 21f)
                        horizontalLineTo(5f)
                        close()
                        moveTo(12.54f, 4.04f)
                        quadTo(12.75f, 3.82f, 12.75f, 3.5f)
                        quadToRelative(0f, -0.33f, -0.21f, -0.54f)
                        reflectiveQuadTo(12f, 2.75f)
                        reflectiveQuadTo(11.46f, 2.96f)
                        reflectiveQuadTo(11.25f, 3.5f)
                        quadToRelative(0f, 0.32f, 0.21f, 0.54f)
                        reflectiveQuadTo(12f, 4.25f)
                        reflectiveQuadTo(12.54f, 4.04f)
                        close()
                    }
                }
                .build()
        }

        val Grading: ImageVector by lazy {
            ImageVector.Builder(
                name = "grading",
                defaultWidth = 24.dp,
                defaultHeight = 24.dp,
                viewportWidth = 24f,
                viewportHeight = 24f,
            )
                .apply {
                    path(
                        fill = SolidColor(Color.Black),
                        fillAlpha = 1f,
                        stroke = null,
                        strokeAlpha = 1f,
                        strokeLineWidth = 1f,
                        strokeLineCap = StrokeCap.Butt,
                        strokeLineJoin = StrokeJoin.Bevel,
                        strokeLineMiter = 1f,
                        pathFillType = PathFillType.NonZero,
                    ) {
                        moveTo(16.43f, 20.98f)
                        lineTo(13.6f, 18.15f)
                        lineTo(15f, 16.75f)
                        lineToRelative(1.43f, 1.43f)
                        lineTo(19.6f, 15f)
                        lineTo(21f, 16.4f)
                        lineToRelative(-4.57f, 4.58f)
                        close()
                        moveTo(3f, 21f)
                        verticalLineTo(19f)
                        horizontalLineToRelative(9f)
                        verticalLineToRelative(2f)
                        horizontalLineTo(3f)
                        close()
                        moveTo(3f, 17f)
                        verticalLineTo(15f)
                        horizontalLineToRelative(9f)
                        verticalLineToRelative(2f)
                        horizontalLineTo(3f)
                        close()
                        moveTo(3f, 13f)
                        verticalLineTo(11f)
                        horizontalLineTo(21f)
                        verticalLineToRelative(2f)
                        horizontalLineTo(3f)
                        close()
                        moveTo(3f, 9f)
                        verticalLineTo(7f)
                        horizontalLineTo(21f)
                        verticalLineTo(9f)
                        horizontalLineTo(3f)
                        close()
                        moveTo(3f, 5f)
                        verticalLineTo(3f)
                        horizontalLineTo(21f)
                        verticalLineTo(5f)
                        horizontalLineTo(3f)
                        close()
                    }
                }
                .build()
        }

        val EventNote: ImageVector by lazy {
            ImageVector.Builder(
                name = "event_note",
                defaultWidth = 24.dp,
                defaultHeight = 24.dp,
                viewportWidth = 24f,
                viewportHeight = 24f,
                autoMirror = true,
            )
                .apply {
                    path(
                        fill = SolidColor(Color.Black),
                        fillAlpha = 1f,
                        stroke = null,
                        strokeAlpha = 1f,
                        strokeLineWidth = 1f,
                        strokeLineCap = StrokeCap.Butt,
                        strokeLineJoin = StrokeJoin.Bevel,
                        strokeLineMiter = 1f,
                        pathFillType = PathFillType.NonZero,
                    ) {
                        moveTo(7f, 14f)
                        verticalLineTo(12f)
                        horizontalLineTo(17f)
                        verticalLineToRelative(2f)
                        horizontalLineTo(7f)
                        close()
                        moveToRelative(0f, 4f)
                        verticalLineTo(16f)
                        horizontalLineToRelative(7f)
                        verticalLineToRelative(2f)
                        horizontalLineTo(7f)
                        close()
                        moveTo(5f, 22f)
                        quadTo(4.18f, 22f, 3.59f, 21.41f)
                        reflectiveQuadTo(3f, 20f)
                        verticalLineTo(6f)
                        quadTo(3f, 5.18f, 3.59f, 4.59f)
                        reflectiveQuadTo(5f, 4f)
                        horizontalLineTo(6f)
                        verticalLineTo(2f)
                        horizontalLineTo(8f)
                        verticalLineTo(4f)
                        horizontalLineToRelative(8f)
                        verticalLineTo(2f)
                        horizontalLineToRelative(2f)
                        verticalLineTo(4f)
                        horizontalLineToRelative(1f)
                        quadToRelative(0.83f, 0f, 1.41f, 0.59f)
                        quadTo(21f, 5.18f, 21f, 6f)
                        verticalLineTo(20f)
                        quadToRelative(0f, 0.82f, -0.59f, 1.41f)
                        reflectiveQuadTo(19f, 22f)
                        horizontalLineTo(5f)
                        close()
                        moveTo(5f, 20f)
                        horizontalLineTo(19f)
                        verticalLineTo(10f)
                        horizontalLineTo(5f)
                        verticalLineTo(20f)
                        close()
                    }
                }
                .build()
        }

    }

    object Outlined {
        val Home: ImageVector by lazy {
            ImageVector.Builder(
                name = "home",
                defaultWidth = 24.dp,
                defaultHeight = 24.dp,
                viewportWidth = 24f,
                viewportHeight = 24f,
            )
                .apply {
                    path(
                        fill = SolidColor(Color.Black),
                        fillAlpha = 1f,
                        stroke = null,
                        strokeAlpha = 1f,
                        strokeLineWidth = 1f,
                        strokeLineCap = StrokeCap.Butt,
                        strokeLineJoin = StrokeJoin.Bevel,
                        strokeLineMiter = 1f,
                        pathFillType = PathFillType.NonZero,
                    ) {
                        moveTo(6f, 19f)
                        horizontalLineTo(9f)
                        verticalLineTo(13f)
                        horizontalLineToRelative(6f)
                        verticalLineToRelative(6f)
                        horizontalLineToRelative(3f)
                        verticalLineTo(10f)
                        lineTo(12f, 5.5f)
                        lineTo(6f, 10f)
                        verticalLineToRelative(9f)
                        close()
                        moveTo(4f, 21f)
                        verticalLineTo(9f)
                        lineTo(12f, 3f)
                        lineToRelative(8f, 6f)
                        verticalLineTo(21f)
                        horizontalLineTo(13f)
                        verticalLineTo(15f)
                        horizontalLineTo(11f)
                        verticalLineToRelative(6f)
                        horizontalLineTo(4f)
                        close()
                        moveToRelative(8f, -8.75f)
                        close()
                    }
                }
                .build()
        }

        val Mail: ImageVector by lazy {
            ImageVector.Builder(
                name = "mail",
                defaultWidth = 24.dp,
                defaultHeight = 24.dp,
                viewportWidth = 24f,
                viewportHeight = 24f,
            )
                .apply {
                    path(
                        fill = SolidColor(Color.Black),
                        fillAlpha = 1f,
                        stroke = null,
                        strokeAlpha = 1f,
                        strokeLineWidth = 1f,
                        strokeLineCap = StrokeCap.Butt,
                        strokeLineJoin = StrokeJoin.Bevel,
                        strokeLineMiter = 1f,
                        pathFillType = PathFillType.NonZero,
                    ) {
                        moveTo(4f, 20f)
                        quadTo(3.18f, 20f, 2.59f, 19.41f)
                        reflectiveQuadTo(2f, 18f)
                        verticalLineTo(6f)
                        quadTo(2f, 5.18f, 2.59f, 4.59f)
                        reflectiveQuadTo(4f, 4f)
                        horizontalLineTo(20f)
                        quadToRelative(0.83f, 0f, 1.41f, 0.59f)
                        quadTo(22f, 5.18f, 22f, 6f)
                        verticalLineTo(18f)
                        quadToRelative(0f, 0.82f, -0.59f, 1.41f)
                        reflectiveQuadTo(20f, 20f)
                        horizontalLineTo(4f)
                        close()
                        moveToRelative(8f, -7f)
                        lineTo(4f, 8f)
                        verticalLineTo(18f)
                        horizontalLineTo(20f)
                        verticalLineTo(8f)
                        lineToRelative(-8f, 5f)
                        close()
                        moveToRelative(0f, -2f)
                        lineTo(20f, 6f)
                        horizontalLineTo(4f)
                        lineToRelative(8f, 5f)
                        close()
                        moveTo(4f, 8f)
                        verticalLineTo(6f)
                        verticalLineTo(8f)
                        verticalLineTo(18f)
                        verticalLineTo(8f)
                        close()
                    }
                }
                .build()
        }

        val AccountBox: ImageVector by lazy {
            ImageVector.Builder(
                name = "account_box",
                defaultWidth = 24.dp,
                defaultHeight = 24.dp,
                viewportWidth = 24f,
                viewportHeight = 24f,
            )
                .apply {
                    path(
                        fill = SolidColor(Color.Black),
                        fillAlpha = 1f,
                        stroke = null,
                        strokeAlpha = 1f,
                        strokeLineWidth = 1f,
                        strokeLineCap = StrokeCap.Butt,
                        strokeLineJoin = StrokeJoin.Bevel,
                        strokeLineMiter = 1f,
                        pathFillType = PathFillType.NonZero,
                    ) {
                        moveTo(5f, 17.85f)
                        quadTo(6.35f, 16.52f, 8.14f, 15.76f)
                        reflectiveQuadTo(12f, 15f)
                        reflectiveQuadToRelative(3.86f, 0.76f)
                        reflectiveQuadTo(19f, 17.85f)
                        verticalLineTo(5f)
                        horizontalLineTo(5f)
                        verticalLineTo(17.85f)
                        close()
                        moveToRelative(9.48f, -5.88f)
                        quadTo(15.5f, 10.95f, 15.5f, 9.5f)
                        reflectiveQuadTo(14.48f, 7.02f)
                        reflectiveQuadTo(12f, 6f)
                        reflectiveQuadTo(9.53f, 7.02f)
                        reflectiveQuadTo(8.5f, 9.5f)
                        reflectiveQuadToRelative(1.03f, 2.47f)
                        reflectiveQuadTo(12f, 13f)
                        reflectiveQuadToRelative(2.48f, -1.03f)
                        close()
                        moveTo(5f, 21f)
                        quadTo(4.18f, 21f, 3.59f, 20.41f)
                        reflectiveQuadTo(3f, 19f)
                        verticalLineTo(5f)
                        quadTo(3f, 4.17f, 3.59f, 3.59f)
                        reflectiveQuadTo(5f, 3f)
                        horizontalLineTo(19f)
                        quadToRelative(0.83f, 0f, 1.41f, 0.59f)
                        reflectiveQuadTo(21f, 5f)
                        verticalLineTo(19f)
                        quadToRelative(0f, 0.82f, -0.59f, 1.41f)
                        reflectiveQuadTo(19f, 21f)
                        horizontalLineTo(5f)
                        close()
                        moveTo(6.73f, 19f)
                        horizontalLineTo(17.28f)
                        quadToRelative(-1.1f, -0.98f, -2.49f, -1.49f)
                        reflectiveQuadTo(12f, 17f)
                        reflectiveQuadTo(9.19f, 17.51f)
                        quadTo(7.78f, 18.02f, 6.73f, 19f)
                        close()
                        moveToRelative(4.21f, -8.44f)
                        quadTo(10.5f, 10.13f, 10.5f, 9.5f)
                        reflectiveQuadTo(10.94f, 8.44f)
                        reflectiveQuadTo(12f, 8f)
                        reflectiveQuadToRelative(1.06f, 0.44f)
                        reflectiveQuadTo(13.5f, 9.5f)
                        reflectiveQuadToRelative(-0.44f, 1.06f)
                        reflectiveQuadTo(12f, 11f)
                        reflectiveQuadTo(10.94f, 10.56f)
                        close()
                        moveTo(12f, 11.43f)
                        close()
                    }
                }
                .build()
        }

        val AttachFile: ImageVector by lazy {
            ImageVector.Builder(
                name = "attach_file",
                defaultWidth = 24.dp,
                defaultHeight = 24.dp,
                viewportWidth = 24f,
                viewportHeight = 24f,
            )
                .apply {
                    path(
                        fill = SolidColor(Color.Black),
                        fillAlpha = 1f,
                        stroke = null,
                        strokeAlpha = 1f,
                        strokeLineWidth = 1f,
                        strokeLineCap = StrokeCap.Butt,
                        strokeLineJoin = StrokeJoin.Bevel,
                        strokeLineMiter = 1f,
                        pathFillType = PathFillType.NonZero,
                    ) {
                        moveTo(18f, 15.75f)
                        quadToRelative(0f, 2.6f, -1.82f, 4.43f)
                        reflectiveQuadTo(11.75f, 22f)
                        reflectiveQuadTo(7.33f, 20.18f)
                        reflectiveQuadTo(5.5f, 15.75f)
                        verticalLineTo(6.5f)
                        quadTo(5.5f, 4.63f, 6.81f, 3.31f)
                        reflectiveQuadTo(10f, 2f)
                        reflectiveQuadToRelative(3.19f, 1.31f)
                        reflectiveQuadTo(14.5f, 6.5f)
                        verticalLineToRelative(8.75f)
                        quadToRelative(0f, 1.15f, -0.8f, 1.95f)
                        reflectiveQuadTo(11.75f, 18f)
                        reflectiveQuadTo(9.8f, 17.2f)
                        reflectiveQuadTo(9f, 15.25f)
                        verticalLineTo(6f)
                        horizontalLineToRelative(2f)
                        verticalLineToRelative(9.25f)
                        quadToRelative(0f, 0.32f, 0.21f, 0.54f)
                        reflectiveQuadTo(11.75f, 16f)
                        reflectiveQuadToRelative(0.54f, -0.21f)
                        reflectiveQuadTo(12.5f, 15.25f)
                        verticalLineTo(6.5f)
                        quadTo(12.48f, 5.45f, 11.76f, 4.72f)
                        reflectiveQuadTo(10f, 4f)
                        reflectiveQuadTo(8.23f, 4.72f)
                        reflectiveQuadTo(7.5f, 6.5f)
                        verticalLineToRelative(9.25f)
                        quadToRelative(-0.02f, 1.77f, 1.22f, 3.01f)
                        quadTo(9.98f, 20f, 11.75f, 20f)
                        quadToRelative(1.75f, 0f, 2.98f, -1.24f)
                        reflectiveQuadTo(16f, 15.75f)
                        verticalLineTo(6f)
                        horizontalLineToRelative(2f)
                        verticalLineToRelative(9.75f)
                        close()
                    }
                }
                .build()
        }

        val FilterList: ImageVector by lazy {
            ImageVector.Builder(
                name = "filter_list",
                defaultWidth = 24.dp,
                defaultHeight = 24.dp,
                viewportWidth = 24f,
                viewportHeight = 24f,
            )
                .apply {
                    path(
                        fill = SolidColor(Color.Black),
                        fillAlpha = 1f,
                        stroke = null,
                        strokeAlpha = 1f,
                        strokeLineWidth = 1f,
                        strokeLineCap = StrokeCap.Butt,
                        strokeLineJoin = StrokeJoin.Bevel,
                        strokeLineMiter = 1f,
                        pathFillType = PathFillType.NonZero,
                    ) {
                        moveTo(10f, 18f)
                        verticalLineTo(16f)
                        horizontalLineToRelative(4f)
                        verticalLineToRelative(2f)
                        horizontalLineTo(10f)
                        close()
                        moveTo(6f, 13f)
                        verticalLineTo(11f)
                        horizontalLineTo(18f)
                        verticalLineToRelative(2f)
                        horizontalLineTo(6f)
                        close()
                        moveTo(3f, 8f)
                        verticalLineTo(6f)
                        horizontalLineTo(21f)
                        verticalLineTo(8f)
                        horizontalLineTo(3f)
                        close()
                    }
                }
                .build()
        }

        val Folder: ImageVector by lazy {
            ImageVector.Builder(
                name = "folder",
                defaultWidth = 24.dp,
                defaultHeight = 24.dp,
                viewportWidth = 24f,
                viewportHeight = 24f,
            )
                .apply {
                    path(
                        fill = SolidColor(Color.Black),
                        fillAlpha = 1f,
                        stroke = null,
                        strokeAlpha = 1f,
                        strokeLineWidth = 1f,
                        strokeLineCap = StrokeCap.Butt,
                        strokeLineJoin = StrokeJoin.Bevel,
                        strokeLineMiter = 1f,
                        pathFillType = PathFillType.NonZero,
                    ) {
                        moveTo(4f, 20f)
                        quadTo(3.18f, 20f, 2.59f, 19.41f)
                        reflectiveQuadTo(2f, 18f)
                        verticalLineTo(6f)
                        quadTo(2f, 5.18f, 2.59f, 4.59f)
                        reflectiveQuadTo(4f, 4f)
                        horizontalLineToRelative(6f)
                        lineToRelative(2f, 2f)
                        horizontalLineToRelative(8f)
                        quadToRelative(0.83f, 0f, 1.41f, 0.59f)
                        quadTo(22f, 7.18f, 22f, 8f)
                        verticalLineTo(18f)
                        quadToRelative(0f, 0.82f, -0.59f, 1.41f)
                        reflectiveQuadTo(20f, 20f)
                        horizontalLineTo(4f)
                        close()
                        moveTo(4f, 18f)
                        horizontalLineTo(20f)
                        verticalLineTo(8f)
                        horizontalLineTo(11.18f)
                        lineToRelative(-2f, -2f)
                        horizontalLineTo(4f)
                        verticalLineTo(18f)
                        close()
                        moveToRelative(0f, 0f)
                        verticalLineTo(6f)
                        verticalLineTo(8f)
                        verticalLineTo(18f)
                        close()
                    }
                }
                .build()
        }

        val Delete: ImageVector by lazy {
            ImageVector.Builder(
                name = "delete",
                defaultWidth = 24.dp,
                defaultHeight = 24.dp,
                viewportWidth = 24f,
                viewportHeight = 24f,
            )
                .apply {
                    path(
                        fill = SolidColor(Color.Black),
                        fillAlpha = 1f,
                        stroke = null,
                        strokeAlpha = 1f,
                        strokeLineWidth = 1f,
                        strokeLineCap = StrokeCap.Butt,
                        strokeLineJoin = StrokeJoin.Bevel,
                        strokeLineMiter = 1f,
                        pathFillType = PathFillType.NonZero,
                    ) {
                        moveTo(7f, 21f)
                        quadTo(6.18f, 21f, 5.59f, 20.41f)
                        reflectiveQuadTo(5f, 19f)
                        verticalLineTo(6f)
                        horizontalLineTo(4f)
                        verticalLineTo(4f)
                        horizontalLineTo(9f)
                        verticalLineTo(3f)
                        horizontalLineToRelative(6f)
                        verticalLineTo(4f)
                        horizontalLineToRelative(5f)
                        verticalLineTo(6f)
                        horizontalLineTo(19f)
                        verticalLineTo(19f)
                        quadToRelative(0f, 0.82f, -0.59f, 1.41f)
                        reflectiveQuadTo(17f, 21f)
                        horizontalLineTo(7f)
                        close()
                        moveTo(17f, 6f)
                        horizontalLineTo(7f)
                        verticalLineTo(19f)
                        horizontalLineTo(17f)
                        verticalLineTo(6f)
                        close()
                        moveTo(9f, 17f)
                        horizontalLineToRelative(2f)
                        verticalLineTo(8f)
                        horizontalLineTo(9f)
                        verticalLineToRelative(9f)
                        close()
                        moveToRelative(4f, 0f)
                        horizontalLineToRelative(2f)
                        verticalLineTo(8f)
                        horizontalLineTo(13f)
                        verticalLineToRelative(9f)
                        close()
                        moveTo(7f, 6f)
                        verticalLineTo(19f)
                        verticalLineTo(6f)
                        close()
                    }
                }
                .build()
        }

        val ArrowBack: ImageVector by lazy {
            ImageVector.Builder(
                name = "arrow_back",
                defaultWidth = 24.dp,
                defaultHeight = 24.dp,
                viewportWidth = 24f,
                viewportHeight = 24f,
                autoMirror = true,
            )
                .apply {
                    path(
                        fill = SolidColor(Color.Black),
                        fillAlpha = 1f,
                        stroke = null,
                        strokeAlpha = 1f,
                        strokeLineWidth = 1f,
                        strokeLineCap = StrokeCap.Butt,
                        strokeLineJoin = StrokeJoin.Bevel,
                        strokeLineMiter = 1f,
                        pathFillType = PathFillType.NonZero,
                    ) {
                        moveTo(7.83f, 13f)
                        lineToRelative(5.6f, 5.6f)
                        lineTo(12f, 20f)
                        lineTo(4f, 12f)
                        lineTo(12f, 4f)
                        lineToRelative(1.43f, 1.4f)
                        lineTo(7.83f, 11f)
                        horizontalLineTo(20f)
                        verticalLineToRelative(2f)
                        horizontalLineTo(7.83f)
                        close()
                    }
                }
                .build()
        }

        val Reply: ImageVector by lazy {
            ImageVector.Builder(
                name = "reply",
                defaultWidth = 24.dp,
                defaultHeight = 24.dp,
                viewportWidth = 24f,
                viewportHeight = 24f,
                autoMirror = true,
            )
                .apply {
                    path(
                        fill = SolidColor(Color.Black),
                        fillAlpha = 1f,
                        stroke = null,
                        strokeAlpha = 1f,
                        strokeLineWidth = 1f,
                        strokeLineCap = StrokeCap.Butt,
                        strokeLineJoin = StrokeJoin.Bevel,
                        strokeLineMiter = 1f,
                        pathFillType = PathFillType.NonZero,
                    ) {
                        moveTo(19f, 19f)
                        verticalLineTo(15f)
                        quadToRelative(0f, -1.25f, -0.88f, -2.13f)
                        reflectiveQuadTo(16f, 12f)
                        horizontalLineTo(6.83f)
                        lineToRelative(3.6f, 3.6f)
                        lineTo(9f, 17f)
                        lineTo(3f, 11f)
                        lineTo(9f, 5f)
                        lineToRelative(1.43f, 1.4f)
                        lineTo(6.83f, 10f)
                        horizontalLineTo(16f)
                        quadToRelative(2.07f, 0f, 3.54f, 1.46f)
                        quadTo(21f, 12.93f, 21f, 15f)
                        verticalLineToRelative(4f)
                        horizontalLineTo(19f)
                        close()
                    }
                }
                .build()
        }

        val Forward: ImageVector by lazy {
            ImageVector.Builder(
                name = "forward",
                defaultWidth = 24.dp,
                defaultHeight = 24.dp,
                viewportWidth = 24f,
                viewportHeight = 24f,
                autoMirror = true,
            )
                .apply {
                    path(
                        fill = SolidColor(Color.Black),
                        fillAlpha = 1f,
                        stroke = null,
                        strokeAlpha = 1f,
                        strokeLineWidth = 1f,
                        strokeLineCap = StrokeCap.Butt,
                        strokeLineJoin = StrokeJoin.Bevel,
                        strokeLineMiter = 1f,
                        pathFillType = PathFillType.NonZero,
                    ) {
                        moveTo(16f, 17f)
                        lineTo(14.58f, 15.6f)
                        lineTo(19.18f, 11f)
                        lineTo(14.58f, 6.4f)
                        lineTo(16f, 5f)
                        lineToRelative(6f, 6f)
                        lineToRelative(-6f, 6f)
                        close()
                        moveTo(2f, 19f)
                        verticalLineTo(15f)
                        quadTo(2f, 12.93f, 3.46f, 11.46f)
                        reflectiveQuadTo(7f, 10f)
                        horizontalLineToRelative(6.18f)
                        lineTo(9.58f, 6.4f)
                        lineTo(11f, 5f)
                        lineToRelative(6f, 6f)
                        lineToRelative(-6f, 6f)
                        lineTo(9.58f, 15.6f)
                        lineTo(13.18f, 12f)
                        horizontalLineTo(7f)
                        quadTo(5.75f, 12f, 4.88f, 12.88f)
                        reflectiveQuadTo(4f, 15f)
                        verticalLineToRelative(4f)
                        horizontalLineTo(2f)
                        close()
                    }
                }
                .build()
        }

    }

}