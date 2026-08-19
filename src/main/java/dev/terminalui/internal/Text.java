package dev.terminalui.internal;

import java.util.*;

final class Text {
    private Text(){}
    static List<String> wrap(String text,int width){
        if(text==null)return List.of(""); List<String> result=new ArrayList<>();
        for(String raw:text.split("\\R",-1)){
            String line="";
            for(String word:raw.split(" ")){if(line.isEmpty())line=word;else if(line.length()+1+word.length()<=width)line+=" "+word;else{result.add(line);line=word;}}
            result.add(line);
        }
        return result;
    }
}
