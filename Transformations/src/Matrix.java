public class Matrix {

	private float[][] values;

	public static Matrix multiply(Matrix a, Matrix b) {
		Matrix result = new Matrix();
		result.values = new float[b.values.length][b.values.length];
		for (int i = 0; i < b.values.length; i++) {
			for (int j = 0; j < b.values.length; j++) {
				float sum = 0;
				for (int k = 0; k < a.values.length; k++) {
					sum += a.values[k][j] * b.values[i][k];
				}
				result.values[i][j] = sum;
			}
		}
		return result;
	}

	public Matrix() {
	}

	public Matrix(Vector3f vector) {
		values = new float[][] { { vector.getX(), vector.getY(), vector.getZ(),
				1 } };
	}

	public void glLoadIdentity() {
		values = new float[4][4];
		for (int i = 0; i < 4; i++) {
			values[i][i] = 1;
		}
	}

	public void glTranslatef(float x, float y, float z) {

	}

	public Vector3f toVector3f() {
		return new Vector3f(values[0][0] / values[0][3], values[0][1]
				/ values[0][3], values[0][2] / values[0][3]);
	}
}
