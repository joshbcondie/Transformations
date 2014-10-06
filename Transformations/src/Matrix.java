public class Matrix {

	private float[][] values;

	public static Matrix multiply(Matrix a, Matrix b) {
		Matrix result = new Matrix();
		result.values = new float[b.values.length][a.values[0].length];
		for (int i = 0; i < b.values.length; i++) {
			for (int j = 0; j < a.values[0].length; j++) {
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
		Matrix translation = new Matrix();
		translation.glLoadIdentity();
		translation.values[3][0] = x;
		translation.values[3][1] = y;
		translation.values[3][2] = z;
		values = Matrix.multiply(this, translation).values;
	}

	public void glScalef(float x, float y, float z) {
		Matrix scale = new Matrix();
		scale.glLoadIdentity();
		scale.values[0][0] = x;
		scale.values[1][1] = y;
		scale.values[2][2] = z;
		values = Matrix.multiply(this, scale).values;
	}

	public void glRotateX(float radians) {
		Matrix rotation = new Matrix();
		rotation.glLoadIdentity();
		rotation.values[1][1] = (float) Math.cos(radians);
		rotation.values[2][1] = -(float) Math.sin(radians);
		rotation.values[1][2] = (float) Math.sin(radians);
		rotation.values[2][2] = (float) Math.cos(radians);
		values = Matrix.multiply(this, rotation).values;
	}

	public void glRotateY(float radians) {
		Matrix rotation = new Matrix();
		rotation.glLoadIdentity();
		rotation.values[0][0] = (float) Math.cos(radians);
		rotation.values[2][0] = (float) Math.sin(radians);
		rotation.values[0][2] = -(float) Math.sin(radians);
		rotation.values[2][2] = (float) Math.cos(radians);
		values = Matrix.multiply(this, rotation).values;
	}

	public Vector3f toVector3f() {
		return new Vector3f(values[0][0] / values[0][3], values[0][1]
				/ values[0][3], values[0][2] / values[0][3]);
	}
}
