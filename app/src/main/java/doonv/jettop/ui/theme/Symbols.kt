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
        val Home24: ImageVector by lazy {
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

        val Mail24: ImageVector by lazy {
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

        val AccountBox24: ImageVector by lazy {
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

        val Edit24: ImageVector by lazy {
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

        val Download24: ImageVector by lazy {
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

        val Settings24: ImageVector by lazy {
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

        val EventNote32: ImageVector by lazy {
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
                        moveTo(7f, 13.83f)
                        verticalLineTo(12f)
                        horizontalLineTo(17f)
                        verticalLineToRelative(1.83f)
                        horizontalLineTo(7f)
                        close()
                        moveTo(7f, 18f)
                        verticalLineTo(16.17f)
                        horizontalLineToRelative(6.99f)
                        verticalLineTo(18f)
                        horizontalLineTo(7f)
                        close()
                        moveTo(4.83f, 22f)
                        quadTo(4.08f, 22f, 3.54f, 21.46f)
                        reflectiveQuadTo(3f, 20.17f)
                        verticalLineTo(5.67f)
                        quadTo(3f, 4.92f, 3.54f, 4.38f)
                        reflectiveQuadTo(4.83f, 3.83f)
                        horizontalLineTo(6.04f)
                        verticalLineTo(2f)
                        horizontalLineTo(7.92f)
                        verticalLineTo(3.83f)
                        horizontalLineToRelative(8.17f)
                        verticalLineTo(2f)
                        horizontalLineToRelative(1.88f)
                        verticalLineTo(3.83f)
                        horizontalLineToRelative(1.21f)
                        quadToRelative(0.75f, 0f, 1.29f, 0.54f)
                        reflectiveQuadTo(21f, 5.67f)
                        verticalLineToRelative(14.5f)
                        quadToRelative(0f, 0.75f, -0.54f, 1.29f)
                        reflectiveQuadTo(19.17f, 22f)
                        horizontalLineTo(4.83f)
                        close()
                        moveToRelative(0f, -1.83f)
                        horizontalLineTo(19.17f)
                        verticalLineTo(9.92f)
                        horizontalLineTo(4.83f)
                        verticalLineTo(20.17f)
                        close()
                    }
                }
                .build()
        }

        val Campaign32: ImageVector by lazy {
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
                        moveTo(18.08f, 12.92f)
                        verticalLineTo(11.08f)
                        horizontalLineTo(22f)
                        verticalLineToRelative(1.83f)
                        horizontalLineTo(18.08f)
                        close()
                        moveTo(19.3f, 20f)
                        lineTo(16.16f, 17.65f)
                        lineToRelative(1.1f, -1.47f)
                        lineToRelative(3.14f, 2.35f)
                        lineTo(19.3f, 20f)
                        close()
                        moveTo(17.28f, 7.81f)
                        lineTo(16.18f, 6.34f)
                        lineTo(19.3f, 4f)
                        lineToRelative(1.1f, 1.47f)
                        lineTo(17.28f, 7.81f)
                        close()
                        moveTo(5.08f, 19f)
                        verticalLineTo(15f)
                        horizontalLineTo(3.83f)
                        quadTo(3.08f, 15f, 2.54f, 14.46f)
                        reflectiveQuadTo(2f, 13.17f)
                        verticalLineTo(10.83f)
                        quadTo(2f, 10.08f, 2.54f, 9.54f)
                        reflectiveQuadTo(3.83f, 9f)
                        horizontalLineTo(8f)
                        lineTo(13f, 6f)
                        verticalLineTo(18f)
                        lineTo(8f, 15f)
                        horizontalLineTo(6.92f)
                        verticalLineToRelative(4f)
                        horizontalLineTo(5.08f)
                        close()
                        moveTo(14f, 15.35f)
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

        val Psychology32: ImageVector by lazy {
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
                        moveTo(11.07f, 14.88f)
                        horizontalLineToRelative(1.83f)
                        lineToRelative(0.13f, -1.2f)
                        quadToRelative(0.23f, -0.07f, 0.43f, -0.19f)
                        reflectiveQuadToRelative(0.35f, -0.27f)
                        lineToRelative(1.12f, 0.45f)
                        lineToRelative(0.9f, -1.53f)
                        lineToRelative(-0.92f, -0.7f)
                        quadToRelative(0.07f, -0.25f, 0.07f, -0.51f)
                        reflectiveQuadTo(14.91f, 10.43f)
                        lineToRelative(0.92f, -0.7f)
                        lineTo(14.93f, 8.19f)
                        lineTo(13.81f, 8.64f)
                        quadTo(13.66f, 8.49f, 13.46f, 8.38f)
                        reflectiveQuadTo(13.03f, 8.18f)
                        lineTo(12.91f, 6.98f)
                        horizontalLineTo(11.07f)
                        lineToRelative(-0.13f, 1.2f)
                        quadTo(10.72f, 8.26f, 10.52f, 8.38f)
                        reflectiveQuadTo(10.18f, 8.64f)
                        lineTo(9.06f, 8.19f)
                        lineTo(8.16f, 9.73f)
                        lineToRelative(0.92f, 0.7f)
                        quadTo(9f, 10.68f, 9f, 10.93f)
                        reflectiveQuadToRelative(0.07f, 0.51f)
                        lineToRelative(-0.92f, 0.7f)
                        lineToRelative(0.9f, 1.53f)
                        lineToRelative(1.12f, -0.45f)
                        quadToRelative(0.15f, 0.15f, 0.35f, 0.27f)
                        reflectiveQuadToRelative(0.43f, 0.19f)
                        lineToRelative(0.13f, 1.2f)
                        close()
                        moveToRelative(-0.2f, -2.83f)
                        quadTo(10.41f, 11.59f, 10.41f, 10.93f)
                        reflectiveQuadTo(10.87f, 9.81f)
                        reflectiveQuadTo(11.99f, 9.35f)
                        reflectiveQuadToRelative(1.12f, 0.46f)
                        reflectiveQuadToRelative(0.46f, 1.12f)
                        reflectiveQuadToRelative(-0.46f, 1.12f)
                        reflectiveQuadToRelative(-1.12f, 0.46f)
                        reflectiveQuadTo(10.87f, 12.05f)
                        close()
                        moveTo(6f, 22f)
                        verticalLineTo(17.7f)
                        quadTo(4.58f, 16.4f, 3.79f, 14.66f)
                        reflectiveQuadTo(3f, 11f)
                        quadTo(3f, 7.25f, 5.63f, 4.63f)
                        reflectiveQuadTo(12f, 2f)
                        quadToRelative(3.13f, 0f, 5.54f, 1.84f)
                        quadToRelative(2.41f, 1.84f, 3.14f, 4.79f)
                        lineTo(22f, 13.86f)
                        quadToRelative(0.12f, 0.43f, -0.16f, 0.79f)
                        reflectiveQuadTo(21.11f, 15f)
                        horizontalLineTo(19f)
                        verticalLineToRelative(3.17f)
                        quadToRelative(0f, 0.76f, -0.54f, 1.3f)
                        reflectiveQuadTo(17.17f, 20f)
                        horizontalLineTo(15f)
                        verticalLineToRelative(2f)
                        horizontalLineTo(6f)
                        close()
                    }
                }
                .build()
        }

        val Group32: ImageVector by lazy {
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
                        moveTo(0.98f, 20f)
                        verticalLineTo(17.35f)
                        quadToRelative(0f, -0.86f, 0.44f, -1.57f)
                        reflectiveQuadTo(2.62f, 14.7f)
                        quadToRelative(1.64f, -0.78f, 3.2f, -1.16f)
                        reflectiveQuadTo(8.98f, 13.17f)
                        reflectiveQuadToRelative(3.17f, 0.38f)
                        reflectiveQuadToRelative(3.19f, 1.16f)
                        quadToRelative(0.75f, 0.37f, 1.2f, 1.08f)
                        reflectiveQuadToRelative(0.45f, 1.57f)
                        verticalLineTo(20f)
                        horizontalLineToRelative(-16f)
                        close()
                        moveToRelative(17.83f, 0f)
                        verticalLineTo(17.22f)
                        quadToRelative(0f, -1.26f, -0.67f, -2.27f)
                        reflectiveQuadToRelative(-1.86f, -1.7f)
                        quadToRelative(1.42f, 0.17f, 2.68f, 0.54f)
                        reflectiveQuadToRelative(2.22f, 0.89f)
                        quadToRelative(0.88f, 0.49f, 1.35f, 1.13f)
                        reflectiveQuadToRelative(0.48f, 1.42f)
                        verticalLineTo(20f)
                        horizontalLineToRelative(-4.2f)
                        close()
                        moveTo(6.2f, 10.86f)
                        quadTo(5.07f, 9.72f, 5.07f, 8.07f)
                        reflectiveQuadTo(6.2f, 5.29f)
                        reflectiveQuadTo(8.98f, 4.16f)
                        reflectiveQuadToRelative(2.78f, 1.13f)
                        reflectiveQuadTo(12.9f, 8.07f)
                        reflectiveQuadToRelative(-1.13f, 2.78f)
                        reflectiveQuadTo(8.98f, 11.99f)
                        reflectiveQuadTo(6.2f, 10.86f)
                        close()
                        moveToRelative(11.32f, 0f)
                        quadToRelative(-1.13f, 1.13f, -2.78f, 1.13f)
                        quadToRelative(-0.28f, 0f, -0.67f, -0.05f)
                        reflectiveQuadTo(13.39f, 11.8f)
                        quadToRelative(0.65f, -0.74f, 1f, -1.7f)
                        reflectiveQuadTo(14.73f, 8.07f)
                        reflectiveQuadTo(14.39f, 6.06f)
                        reflectiveQuadToRelative(-1f, -1.71f)
                        quadTo(13.72f, 4.24f, 14.06f, 4.2f)
                        reflectiveQuadTo(14.73f, 4.16f)
                        quadToRelative(1.65f, 0f, 2.78f, 1.13f)
                        reflectiveQuadToRelative(1.13f, 2.78f)
                        reflectiveQuadToRelative(-1.13f, 2.78f)
                        close()
                    }
                }
                .build()
        }

        val AssignmentTurnedIn32: ImageVector by lazy {
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
                        moveTo(10.59f, 15.96f)
                        lineTo(17.6f, 8.95f)
                        lineTo(16.31f, 7.66f)
                        lineToRelative(-5.72f, 5.72f)
                        lineTo(7.68f, 10.47f)
                        lineTo(6.4f, 11.75f)
                        lineToRelative(4.19f, 4.21f)
                        close()
                        moveTo(4.83f, 21f)
                        quadTo(4.08f, 21f, 3.54f, 20.46f)
                        reflectiveQuadTo(3f, 19.17f)
                        verticalLineTo(4.83f)
                        quadTo(3f, 4.08f, 3.54f, 3.54f)
                        reflectiveQuadTo(4.83f, 3f)
                        horizontalLineTo(9.34f)
                        quadTo(9.6f, 2.11f, 10.33f, 1.55f)
                        reflectiveQuadTo(12f, 1f)
                        reflectiveQuadToRelative(1.67f, 0.55f)
                        reflectiveQuadTo(14.66f, 3f)
                        horizontalLineToRelative(4.51f)
                        quadToRelative(0.76f, 0f, 1.3f, 0.54f)
                        reflectiveQuadTo(21f, 4.83f)
                        verticalLineTo(19.17f)
                        quadToRelative(0f, 0.76f, -0.54f, 1.3f)
                        reflectiveQuadTo(19.17f, 21f)
                        horizontalLineTo(4.83f)
                        close()
                        moveTo(12.56f, 3.96f)
                        quadTo(12.79f, 3.73f, 12.79f, 3.4f)
                        reflectiveQuadTo(12.56f, 2.84f)
                        reflectiveQuadTo(12f, 2.61f)
                        reflectiveQuadTo(11.44f, 2.84f)
                        reflectiveQuadTo(11.21f, 3.4f)
                        reflectiveQuadToRelative(0.23f, 0.56f)
                        reflectiveQuadTo(12f, 4.19f)
                        reflectiveQuadTo(12.56f, 3.96f)
                        close()
                    }
                }
                .build()
        }

        val Grading32: ImageVector by lazy {
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
                        moveTo(16.45f, 20.98f)
                        lineTo(13.69f, 18.22f)
                        lineToRelative(1.29f, -1.28f)
                        lineToRelative(1.47f, 1.47f)
                        lineToRelative(3.27f, -3.27f)
                        lineToRelative(1.29f, 1.28f)
                        lineToRelative(-4.57f, 4.57f)
                        close()
                        moveTo(3f, 21f)
                        verticalLineTo(19.17f)
                        horizontalLineToRelative(9f)
                        verticalLineTo(21f)
                        horizontalLineTo(3f)
                        close()
                        moveTo(3f, 16.96f)
                        verticalLineTo(15.12f)
                        horizontalLineToRelative(9f)
                        verticalLineToRelative(1.83f)
                        horizontalLineTo(3f)
                        close()
                        moveTo(3f, 12.92f)
                        verticalLineTo(11.08f)
                        horizontalLineTo(21f)
                        verticalLineToRelative(1.83f)
                        horizontalLineTo(3f)
                        close()
                        moveTo(3f, 8.88f)
                        verticalLineTo(7.04f)
                        horizontalLineTo(21f)
                        verticalLineTo(8.88f)
                        horizontalLineTo(3f)
                        close()
                        moveTo(3f, 4.83f)
                        verticalLineTo(3f)
                        horizontalLineTo(21f)
                        verticalLineTo(4.83f)
                        horizontalLineTo(3f)
                        close()
                    }
                }
                .build()
        }

    }

    object Outlined {
        val Home24: ImageVector by lazy {
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

        val Mail24: ImageVector by lazy {
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

        val AccountBox24: ImageVector by lazy {
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

        val AttachFile20: ImageVector by lazy {
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
                        moveTo(17.4f, 16.2f)
                        quadToRelative(0f, 2.25f, -1.58f, 3.82f)
                        reflectiveQuadTo(12f, 21.6f)
                        quadToRelative(-2.27f, 0f, -3.84f, -1.64f)
                        quadTo(6.6f, 18.33f, 6.6f, 16.02f)
                        verticalLineTo(6.3f)
                        quadTo(6.6f, 4.67f, 7.74f, 3.54f)
                        reflectiveQuadTo(10.5f, 2.4f)
                        quadToRelative(1.65f, 0f, 2.78f, 1.2f)
                        reflectiveQuadTo(14.4f, 6.47f)
                        verticalLineTo(15.6f)
                        quadToRelative(0f, 1f, -0.7f, 1.7f)
                        reflectiveQuadTo(12f, 18f)
                        quadToRelative(-1.02f, 0f, -1.71f, -0.73f)
                        reflectiveQuadTo(9.6f, 15.5f)
                        verticalLineTo(6f)
                        horizontalLineToRelative(1.8f)
                        verticalLineToRelative(9.6f)
                        quadToRelative(0f, 0.26f, 0.17f, 0.43f)
                        reflectiveQuadTo(12f, 16.2f)
                        reflectiveQuadToRelative(0.43f, -0.17f)
                        reflectiveQuadTo(12.6f, 15.6f)
                        verticalLineTo(6.3f)
                        quadToRelative(0f, -0.88f, -0.61f, -1.49f)
                        reflectiveQuadTo(10.5f, 4.2f)
                        reflectiveQuadTo(9.01f, 4.84f)
                        reflectiveQuadTo(8.4f, 6.35f)
                        verticalLineTo(16.2f)
                        quadToRelative(0f, 1.5f, 1.05f, 2.54f)
                        quadTo(10.5f, 19.77f, 12f, 19.8f)
                        reflectiveQuadToRelative(2.55f, -1.07f)
                        reflectiveQuadTo(15.6f, 16.08f)
                        verticalLineTo(6f)
                        horizontalLineToRelative(1.8f)
                        verticalLineTo(16.2f)
                        close()
                    }
                }
                .build()
        }

        val FilterList24: ImageVector by lazy {
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

        val Folder24: ImageVector by lazy {
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

        val Delete24: ImageVector by lazy {
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

        val ArrowBack24: ImageVector by lazy {
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

        val Reply24: ImageVector by lazy {
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

        val Forward24: ImageVector by lazy {
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
