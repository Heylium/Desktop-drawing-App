//package view
//
//import androidx.compose.foundation.Canvas
//import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Paint
//import androidx.compose.ui.graphics.Path
//import androidx.compose.ui.graphics.drawscope.DrawScope
//
////typealias DrawAction = DrawScope.() -> Unit
//
//@Composable
//fun CustomDraw(actions: List<DrawAction>) {
//    Box(modifier = Modifier.fillMaxSize()) {
//        Canvas(modifier = Modifier.fillMaxSize()) { drawScope ->
//            actions.forEach { action ->
//                drawScope.action()
//            }
//        }
//    }
//}
//
//// 创建 Path 绘制操作
//fun createPathAction(path: Path, paint: Paint): DrawAction = {
//    drawPath(path, paint)
//}
//
//// 创建 Arc 绘制操作
//fun createArcAction(color: Color, startAngle: Float, sweepAngle: Float, useCenter: Boolean, style: DrawStyle): DrawAction = {
//    drawArc(color, startAngle, sweepAngle, useCenter, style)
//}