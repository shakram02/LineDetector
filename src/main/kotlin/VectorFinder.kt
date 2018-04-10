typealias Point = Pair<Double, Double>

class VectorFinder(private val x: List<Double>, private val y: List<Double>) {


    fun clusterLines(): Point {
        val results = HashMap<Point, HashSet<Point>>()
        val marked = HashSet<Point>()

        val norm = normalTransform(x).zip(normalTransform(y))
        val points = x.zip(y)

        val threshold = 20


        for ((index, point) in points.withIndex()) {
            if (marked.contains(point)) continue

            for ((otherIndex, otherPoint) in points.withIndex()) {
                if (marked.contains(otherPoint) || point == otherPoint) continue

                val angle = getAngle(norm[index], norm[otherIndex])
                println(angle)

                if (angle <= threshold) {
                    if (results.containsKey(point)) {
                        results[point]!!.add(otherPoint)
                    } else {
                        results[point] = hashSetOf(otherPoint)
                    }

                    marked.add(otherPoint)
                }
            }

            marked.add(point)
        }

        for (entry in results) {
            println(entry)
        }
        return Pair(3.0, 4.0)
    }

    private fun getAngle(p1: Point, p2: Point): Double {
        val deltaX = Math.abs(p1.first - p2.first)
        val deltaY = Math.abs(p1.second - p2.second)

        return Math.toDegrees(Math.atan2(deltaY, deltaX))
    }

    private fun normalize(p: Double, min: Double, max: Double): Double {
        return (p - min) / (max - min)
    }

    private fun normalTransform(collection: List<Double>): List<Double> {
        val min = collection.min()!!
        val max = collection.max()!!

        val range = max - min
        return collection.map { item -> (item - min) / range }
    }
}