package linefinder

typealias Point = Pair<Double, Double>

/**
 * Given a set of points that nearly form multiple straight lines
 * [LineFinder] tries to find points forming the lines with a certain [thresholdDegrees]
 *
 *
 * @param points Set of points as (x,y) pairs to work upon
 * @param thresholdDegrees comparison tolerance in degrees, since points aren't strictly required to
 * form an exact straight line
 */
class LineFinder(private val points: List<Point>, private val thresholdDegrees: Int = 5) {

    /**
     * Finds points on same line by finding the angle between the vectors formed by each **triplets** of points
     * It checks each triplet if it forms a near line shape
     *
     * Unmatched points are inserted in separate entries in the result table
     *
     * @return [HashMap] of key point and set of points that form the line **including** the key point
     */
    fun clusterLines(): HashMap<Point, HashSet<Point>> {
        val results = HashMap<Point, HashSet<Point>>()
        val marked = HashSet<Point>()

        for ((root_index, root_point) in points.withIndex()) {
            if (marked.contains(root_point)) continue

            for ((parent_index, parent_point) in points.withIndex()) {
                if (marked.contains(parent_point) || root_index == parent_index) continue

                for ((child_index, child_point) in points.withIndex()) {

                    if (marked.contains(child_point)
                            || parent_index == child_index
                            || root_index == child_index) continue


                    // Angles could be close to 0 or close to 180
                    val angle = getAngle(child_point, parent_point, root_point)
                    if (angle <= thresholdDegrees || angle >= 180 - thresholdDegrees) {

                        marked.add(root_point)
                        marked.add(parent_point)
                        marked.add(child_point)

                        if (results.containsKey(root_point)) {
                            results[root_point]!!.add(child_point)
                        } else {
                            results[root_point] = hashSetOf(root_point, parent_point, child_point)
                        }
                    }
                }
            }
        }

        val unmatched = points.filter { p -> !marked.contains(p) }
        for (point in unmatched) {
            results[point] = hashSetOf(point)
        }

        return results
    }

    companion object {

        fun getAngle(p0: Point, p1: Point, c: Point): Double {
            val p0c = Math.sqrt(Math.pow(c.first - p0.first, 2.0) +
                    Math.pow(c.second - p0.second, 2.0))
            val p1c = Math.sqrt(Math.pow(c.first - p1.first, 2.0) +
                    Math.pow(c.second - p1.second, 2.0))
            val p0p1 = Math.sqrt(Math.pow(p1.first - p0.first, 2.0) +
                    Math.pow(p1.second - p0.second, 2.0))

            // Take care that rounding affects the acos function heavily, that's why we're rounding to millionth
            val x = ((p1c * p1c + p0c * p0c - p0p1 * p0p1) / (2.0 * p1c * p0c)).roundMillionth()
            return Math.toDegrees(Math.acos(x))
        }
    }
}

fun Double.roundHundredth(): Double {
    return Math.round(this * 100) / 100.0
}

fun Double.roundMillionth(): Double {
    return Math.round(this * 1000000) / 1000000.0
}