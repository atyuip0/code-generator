package com.zhh.cg.util;


import com.zhh.cg.api.java.FullyQualifiedJavaType;

import java.util.Locale;

/**
 * The Class JavaBeansUtil.
 *
 */
public class JavaBeansUtil {

    /**
     * Instantiates a new java beans util.
     */
    private JavaBeansUtil() {
        super();
    }

    /**
     * JavaBeans rules:
     * 
     * eMail &gt; geteMail() firstName &gt; getFirstName() URL $gt; getURL() XAxis &gt; getXAxis() a &gt; getA() B &gt;
     * invalid - this method assumes that this is not the case. Call getValidPropertyName first. Yaxis &gt; invalid -
     * this method assumes that this is not the case. Call getValidPropertyName first.
     *
     * @param property
     *            the property
     * @param fullyQualifiedJavaType
     *            the fully qualified java type
     * @return the getter method name
     */
    public static String getGetterMethodName(String property,
            FullyQualifiedJavaType fullyQualifiedJavaType) {
        StringBuilder sb = new StringBuilder();

        sb.append(property);
        if (Character.isLowerCase(sb.charAt(0))) {
            if (sb.length() == 1 || !Character.isUpperCase(sb.charAt(1))) {
                sb.setCharAt(0, Character.toUpperCase(sb.charAt(0)));
            }
        }

        if (fullyQualifiedJavaType.equals(FullyQualifiedJavaType
                .getBooleanPrimitiveInstance())) {
            sb.insert(0, "is"); //$NON-NLS-1$
        } else {
            sb.insert(0, "get"); //$NON-NLS-1$
        }

        return sb.toString();
    }

    /**
     * JavaBeans rules:
     * 
     * eMail &gt; seteMail() firstName &gt; setFirstName() URL &gt; setURL() XAxis &gt; setXAxis() a &gt; setA() B &gt;
     * invalid - this method assumes that this is not the case. Call getValidPropertyName first. Yaxis &gt; invalid -
     * this method assumes that this is not the case. Call getValidPropertyName first.
     *
     * @param property
     *            the property
     * @return the setter method name
     */
    public static String getSetterMethodName(String property) {
        StringBuilder sb = new StringBuilder();

        sb.append(property);
        if (Character.isLowerCase(sb.charAt(0))) {
            if (sb.length() == 1 || !Character.isUpperCase(sb.charAt(1))) {
                sb.setCharAt(0, Character.toUpperCase(sb.charAt(0)));
            }
        }

        sb.insert(0, "set"); //$NON-NLS-1$

        return sb.toString();
    }

    /**
     * Gets the camel case string.
     *
     * @param inputString
     *            the input string
     * @param firstCharacterUppercase
     *            the first character uppercase
     * @return the camel case string
     */
    public static String getCamelCaseString(String inputString,
            boolean firstCharacterUppercase) {
        StringBuilder sb = new StringBuilder();

        boolean nextUpperCase = false;
        for (int i = 0; i < inputString.length(); i++) {
            char c = inputString.charAt(i);

            switch (c) {
            case '_':
            case '-':
            case '@':
            case '$':
            case '#':
            case ' ':
            case '/':
            case '&':
                if (sb.length() > 0) {
                    nextUpperCase = true;
                }
                break;

            default:
                if (nextUpperCase) {
                    sb.append(Character.toUpperCase(c));
                    nextUpperCase = false;
                } else {
                    sb.append(Character.toLowerCase(c));
                }
                break;
            }
        }

        if (firstCharacterUppercase) {
            sb.setCharAt(0, Character.toUpperCase(sb.charAt(0)));
        }

        return sb.toString();
    }

    /**
     * This method ensures that the specified input string is a valid Java property name. The rules are as follows:
     * 
     * 1. If the first character is lower case, then OK 2. If the first two characters are upper case, then OK 3. If the
     * first character is upper case, and the second character is lower case, then the first character should be made
     * lower case
     * 
     * eMail &gt; eMail firstName &gt; firstName URL &gt; URL XAxis &gt; XAxis a &gt; a B &gt; b Yaxis &gt; yaxis
     *
     * @param inputString
     *            the input string
     * @return the valid property name
     */
    public static String getValidPropertyName(String inputString) {
        String answer;

        if (inputString == null) {
            answer = null;
        } else if (inputString.length() < 2) {
            answer = inputString.toLowerCase(Locale.US);
        } else {
            if (Character.isUpperCase(inputString.charAt(0))
                    && !Character.isUpperCase(inputString.charAt(1))) {
                answer = inputString.substring(0, 1).toLowerCase(Locale.US)
                        + inputString.substring(1);
            } else {
                answer = inputString;
            }
        }

        return answer;
    }

}
