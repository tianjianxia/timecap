package com.timecap.apiService.Service;

import htmlflow.HtmlView;
import htmlflow.StaticHtml;

public class HtmlRender {
    public String getTemplate(String content, String path){
        HtmlView view = StaticHtml.view(v ->
                v.html()
                        .body()
                        .p()
                        .text(content)
                        .__()
                        .img()
                        .attrSrc(path)
                        .__()
                        .__()
        );
        return view.render();
    }
}
