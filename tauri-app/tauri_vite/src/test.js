import {crate, all} from "mathjs"

function calArcCenter(p1, p2, p3) {
    // 定义三个点的坐标
    let A = math.matrix([x1, y1]);
    let B = math.matrix([x2, y2]);
    let C = math.matrix([x3, y3]);

// 计算向量 AB 和 AC
    let AB = math.subtract(B, A);
    let AC = math.subtract(C, A);

// 计算中点 M 和 N
    let M = math.multiply(math.add(A, B), 0.5);
    let N = math.multiply(math.add(A, C), 0.5);

// 计算向量 P 和 Q
    let P = math.matrix([AB.get([1]), -AB.get([0])]);
    let Q = math.matrix([AC.get([1]), -AC.get([0])]);

// 构造过 M、N 点且方向为 P、Q 的直线的参数表示
// X = M + tP, Y = N + sQ
// 解这个二元一次方程组得到参数 t 和 s
    let coefficients = math.transpose(math.matrix([P.toArray(), Q.toArray()]));
    let constants = math.subtract(N, M).toArray();
    let solution = math.lusolve(coefficients, constants);

// 代入 t 得到圆心坐标
    let center = math.add(M, math.multiply(P, solution[0])).toArray();
}