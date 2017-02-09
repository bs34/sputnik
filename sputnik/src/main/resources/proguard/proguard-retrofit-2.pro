-dontwarn retrofit2.**
-keep class retrofit2.** { *; }
-keepattributes Exceptions
-keepclassmembernames interface * {
    @retrofit2.http.* <methods>;
}