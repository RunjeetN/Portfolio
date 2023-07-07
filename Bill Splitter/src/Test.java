import java.util.Arrays;

public class Test {
    public static void main(String[] args) {
        int[][] img = {{1,2,4,3},{5,7,6,8},{3,2,1,4},{2,2,1,0}};
        rotate(img);
    }

    public static void rotate(int[][] image) {
        int N = image.length;
        int[][] newImage = new int[N][N];

        for (int i = N - 1; i >= 0; i--) {
            for(int k = 0; k < N; k++){
                newImage[(i - N + 1) * -1][k] = image[k][i];
            }
        }
        for(int i = 0; i < N; i++){
            System.out.println(Arrays.toString(newImage[i]));
        }

    }
}
