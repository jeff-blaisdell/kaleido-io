/*
 * Direct copy of Spring's PathMatcher found here:
 * https://github.com/spring-projects/spring-framework/blob/master/spring-core/src/main/java/org/springframework/util/PathMatcher.java
 */
package io.kaleido.auth.security


public interface PathMatcher {

    boolean isPattern(String path);
    boolean match(String pattern, String path);
    boolean matchStart(String pattern, String path);
    String extractPathWithinPattern(String pattern, String path);
    Map<String, String> extractUriTemplateVariables(String pattern, String path);
    Comparator<String> getPatternComparator(String path);
    String combine(String pattern1, String pattern2);

}
