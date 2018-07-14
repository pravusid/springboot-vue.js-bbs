package kr.pravusid.helper;

public final class StringHelper {

    public static String escapeCharacters(String str) {
        return str.replace("\n", "<br>")
                .replace("<script>", "&lt;script&gt;")
                .replace("</script>", "&lt;/script&gt;");
    }

}
