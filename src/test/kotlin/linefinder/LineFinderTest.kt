package linefinder

import org.junit.Assert
import org.junit.Test

class LineFinderTest {
    @Test
    fun testAngleUnaligned() {

        val p1 = Point(1.0, 1.0)
        val p2 = Point(4.0, 18.0)
        val p3 = Point(5.0, 20.0)

        var angle = LineFinder.getAngle(p1, p2, p3).roundHundredth()
        println("Angle: $angle")
        Assert.assertTrue(angle != 0.0)

        angle = LineFinder.getAngle(p2, p3, p1).roundHundredth()
        println("Angle: $angle")
        Assert.assertTrue(angle != 0.0)

        angle = LineFinder.getAngle(p3, p1, p2).roundHundredth()
        println("Angle: $angle")
        Assert.assertTrue(angle != 0.0)
    }

    @Test
    fun testOneLinePositiveSlope() {
        val points = ArrayList<Point>()
        // 3x + 10
        points.add(Point(0.0, 10.0))
        points.add(Point(4.0, 22.0))
        points.add(Point(5.0, 25.0))
        points.add(Point(8.0, 34.0))
        points.add(Point(9.0, 37.0))

        val finder = LineFinder(points, 3)
        val results = finder.clusterLines()

        Assert.assertEquals(1, results.size)
    }

    @Test
    fun twoLinesBottomLeftTopRight() {
        val firstCluster = hashSetOf(
                Point(980.5, 596.0), Point(726.5, 549.0),
                Point(851.0, 572.5), Point(593.5, 525.0))

        val secondCluster = hashSetOf(
                Point(877.5, 299.0), Point(754.5, 281.0),
                Point(633.0, 261.5), Point(1022.5, 318.5)
        )

        runTest(listOf(firstCluster, secondCluster))
    }

    @Test
    fun testTwoLinesTopLeftBottomRight() {
        val firstCluster = hashSetOf(
                Point(-318.0, 185.0),
                Point(-172.0, 105.0),
                Point(-36.0, 45.0),
                Point(92.0, -15.0),
                Point(210.0, -70.0))

        // Cluster 2
        val secondCluster = hashSetOf(
                Point(-165.0, -8.0),
                Point(-60.0, -73.0),
                Point(100.0, -125.0),
                Point(204.0, -180.0),
                Point(-274.0, 59.0)
        )

        runTest(listOf(firstCluster, secondCluster))
    }

    private fun runTest(clusters: List<HashSet<Point>>) {
        fun validateCluster(expected: HashSet<Point>, found: List<HashSet<Point>>): Boolean {
            for (list in found) {
                if (list.all { i -> expected.contains(i) }) {
                    return true
                }
            }

            return false
        }

        val points = mutableListOf<Point>()
        for (cluster in clusters) {
            points.addAll(cluster)
        }
        points.shuffle()

        val finder = LineFinder(points)
        val results = finder.clusterLines()

        Assert.assertEquals(clusters.size, results.size)

        val values = results.values.toList()

        for (cluster in clusters) {
            Assert.assertTrue(validateCluster(cluster.toHashSet(), values))
        }
    }
}