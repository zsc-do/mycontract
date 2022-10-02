package cn.it.mycontract.security;

import org.springframework.util.StringUtils;

import org.springframework.util.StreamUtils;
import org.springframework.util.StringUtils;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


public class XssFiltrer extends HttpServletRequestWrapper {

    private static String key = "and|exec|insert|select|delete|update|count|*|%|chr|mid|master|truncate|char|declare|;|or|-|+";
    private static Set<String> notAllowedKeyWords = new HashSet<String>(0);
    private static String replacedString = "INVALID";

    static {
        String keyStr[] = key.split("\\|");
        for (String str : keyStr) {
            notAllowedKeyWords.add(str);
        }
    }

    private String currentUrl;
    private byte[] body;

    /**
     * 构造函数
     * @param servletRequest
     * @throws IOException
     */
    public XssFiltrer(HttpServletRequest servletRequest) throws IOException {
        super(servletRequest);
        currentUrl = servletRequest.getRequestURI();
        //获取到json数据
        this.body = StreamUtils.copyToByteArray(servletRequest.getInputStream());
    }

    /**
     * 重写该方法过滤json数据
     * @return
     * @throws IOException
     */
    @Override
    public ServletInputStream getInputStream() throws IOException {
        ServletInputStream inputStream = null;
        String bodyStr = new String(body);
        if (!StringUtils.isEmpty(bodyStr)) {
            bodyStr = xssEncode(bodyStr, 1);
            bodyStr = cleanSqlKeyWords(bodyStr);
            final ByteArrayInputStream bais = new ByteArrayInputStream(bodyStr.getBytes());
            return new ServletInputStream() {
                @Override
                public boolean isFinished() {
                    return false;
                }

                @Override
                public boolean isReady() {
                    return false;
                }

                @Override
                public void setReadListener(ReadListener readListener) {

                }

                @Override
                public int read() throws IOException {
                    return bais.read();
                }
            };
        }
        return inputStream;
    }

    /**
     * 将容易引起xss漏洞的半角字符直接替换成全角字符
     * @param s
     * @param type
     * @return
     */
    private static String xssEncode(String s, int type) {
        if (s == null || s.isEmpty()) {
            return s;
        }
        StringBuilder sb = new StringBuilder(s.length() + 16);
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (type == 0) {
                switch (c) {
                    case '\'':
                        // 全角单引号
                        sb.append('‘');
                        break;
                    case '\"':
                        // 全角双引号
                        sb.append('“');
                        break;
                    case '>':
                        // 全角大于号
                        sb.append('＞');
                        break;
                    case '<':
                        // 全角小于号
                        sb.append('＜');
                        break;
                    case '&':
                        // 全角&符号
                        sb.append('＆');
                        break;
                    case '\\':
                        // 全角斜线
                        sb.append('＼');
                        break;
                    case '#':
                        // 全角井号
                        sb.append('＃');
                        break;
                    // < 字符的 URL 编码形式表示的 ASCII 字符（十六进制格式） 是: %3c
                    case '%':
                        processUrlEncoder(sb, s, i);
                        break;
                    default:
                        sb.append(c);
                        break;
                }
            } else {
                switch (c) {
                    case '>':
                        // 全角大于号
                        sb.append('＞');
                        break;
                    case '<':
                        // 全角小于号
                        sb.append('＜');
                        break;
                    case '&':
                        // 全角&符号
                        sb.append('＆');
                        break;
                    case '#':
                        // 全角井号
                        sb.append('＃');
                        break;
                    // < 字符的 URL 编码形式表示的 ASCII 字符（十六进制格式） 是: %3c
                    case '%':
                        processUrlEncoder(sb, s, i);
                        break;
                    default:
                        sb.append(c);
                        break;
                }
            }

        }
        return sb.toString();
    }

    public static void processUrlEncoder(StringBuilder sb, String s, int index) {
        if (s.length() >= index + 2) {
            // %3c, %3C
            if (s.charAt(index + 1) == '3' && (s.charAt(index + 2) == 'c' || s.charAt(index + 2) == 'C')) {
                sb.append('＜');
                return;
            }
            // %3c (0x3c=60)
            if (s.charAt(index + 1) == '6' && s.charAt(index + 2) == '0') {
                sb.append('＜');
                return;
            }
            // %3e, %3E
            if (s.charAt(index + 1) == '3' && (s.charAt(index + 2) == 'e' || s.charAt(index + 2) == 'E')) {
                sb.append('＞');
                return;
            }
            // %3e (0x3e=62)
            if (s.charAt(index + 1) == '6' && s.charAt(index + 2) == '2') {
                sb.append('＞');
                return;
            }
        }
        sb.append(s.charAt(index));
    }


    /**
     * 覆盖getParameter方法，将参数名和参数值都做xss过滤。
     * 如果需要获得原始的值，则通过super.getParameterValues(name)来获取
     * getParameterNames,getParameterValues和getParameterMap也可能需要覆盖
     */
    @Override
    public String getParameter(String parameter) {
        String value = super.getParameter(parameter);
        if (value == null) {
            return null;
        }
        return cleanXSS(value);
    }

    @Override
    public String[] getParameterValues(String parameter) {
        String[] values = super.getParameterValues(parameter);
        if (values == null) {
            return null;
        }
        int count = values.length;
        String[] encodedValues = new String[count];
        for (int i = 0; i < count; i++) {
            encodedValues[i] = cleanXSS(values[i]);
        }
        return encodedValues;
    }

    @Override
    public Map<String, String[]> getParameterMap() {
        Map<String, String[]> values = super.getParameterMap();
        if (values == null) {
            return null;
        }
        Map<String, String[]> result = new HashMap<>();
        for (String key : values.keySet()) {
            String encodedKey = cleanXSS(key);
            int count = values.get(key).length;
            String[] encodedValues = new String[count];
            for (int i = 0; i < count; i++) {
                encodedValues[i] = cleanXSS(values.get(key)[i]);
            }
            result.put(encodedKey, encodedValues);
        }
        return result;
    }

    /**
     * 覆盖getHeader方法，将参数名和参数值都做xss过滤。
     * 如果需要获得原始的值，则通过super.getHeaders(name)来获取
     * getHeaderNames 也可能需要覆盖
     */
    @Override
    public String getHeader(String name) {
        String value = super.getHeader(name);
        if (value == null) {
            return null;
        }
        return cleanXSS(value);
    }

    private String cleanXSS(String valueP) {
        String value = valueP.replaceAll("<", "&lt;").replaceAll(">", "&gt;");
        value = value.replaceAll("<", "& lt;").replaceAll(">", "& gt;");
        value = value.replaceAll("\\(", "& #40;").replaceAll("\\)", "& #41;");
        value = value.replaceAll("'", "& #39;");
        value = value.replaceAll("eval\\((.*)\\)", "");
        value = value.replaceAll("[\\\"\\\'][\\s]*javascript:(.*)[\\\"\\\']", "\"\"");
        value = value.replaceAll("script", "");
        value = cleanSqlKeyWords(value);
        return value;
    }

    private String cleanSqlKeyWords(String value) {
        String paramValue = value;
        for (String keyword : notAllowedKeyWords) {
            if (paramValue.length() > keyword.length() + 3
                    && (paramValue.contains(" " + keyword) || paramValue.contains(keyword + " ") || paramValue.contains(" " + keyword + " "))) {
                paramValue = StringUtils.replace(paramValue, keyword, replacedString);
                System.out.println(this.currentUrl + "已被过滤，因为参数中包含不允许sql的关键词(" + keyword
                        + ")" + ";参数：" + value + ";过滤后的参数：" + paramValue);
            }
        }
        return paramValue;
    }

}

