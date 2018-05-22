package zia.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class NetUtil {

    private static final boolean openLog = false;

    public static String getHtml(String url) throws IOException {
        return getHtml(url, "utf-8");
    }

    public static String getHtml(String url, String encodeType) throws IOException {
        return get(url, encodeType, null);
    }

    public static String postHtml(String url, ParametersBuilder parametersBuilder) throws IOException {
        return postHtml(url, parametersBuilder, "utf-8");
    }

    public static String postHtml(String url, ParametersBuilder parametersBuilder, String encodeType) throws IOException {
        return get(url, encodeType, parametersBuilder);
    }

    private static String get(String url, String encodeType, ParametersBuilder parametersBuilder) throws IOException {
        if (openLog) System.out.println("connect : " + url);
        if (openLog) System.out.println(parametersBuilder);
        StringBuilder result = new StringBuilder();
        BufferedReader in = null;
        // post请求的参数
        URL realUrl = new URL(url);
        // 打开和URL之间的连接
        HttpURLConnection connection = (HttpURLConnection) realUrl.openConnection();
        connection.setDoInput(true);
        connection.setRequestMethod("GET");
        connection.setConnectTimeout(5000);
        connection.setReadTimeout(5000);
        connection.setRequestProperty("accept", "*/*");
        connection.setRequestProperty("connection", "Keep-Alive");
        connection.setRequestProperty("Charsert", "UTF-8");
        connection.setRequestProperty("user-agent",
                "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
        connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        if (parametersBuilder != null) {
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            OutputStream outputStream = connection.getOutputStream();
            outputStream.write(parametersBuilder.getParameters().getBytes());
            outputStream.flush();
            outputStream.close();
        }
        // 建立实际的连接
        connection.connect();
        // 定义 BufferedReader输入流来读取URL的响应
        in = new BufferedReader(new InputStreamReader(connection.getInputStream(), encodeType));
        String line;
        while ((line = in.readLine()) != null) {
            result.append(line);
        }
        in.close();
        connection.disconnect();
        if (openLog) System.out.println(result);
        return result.toString();
    }

    public static class ParametersBuilder {
        private StringBuilder parameters = new StringBuilder();

        public ParametersBuilder put(String key, String value) {
            parameters.append(key);
            parameters.append("=");
            parameters.append(value);
            parameters.append("&");
            return this;
        }

        public String getParameters() {
            int lastPosition = parameters.length();
            parameters.delete(lastPosition - 1, lastPosition);
            return parameters.toString();
        }

        @Override
        public String toString() {
            return getParameters();
        }
    }
}
