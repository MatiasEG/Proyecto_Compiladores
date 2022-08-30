///[Error:"Search Result Not Selected);|11]

public void onEventSaveArticle() {
        thread = new Thread(() -> {
            if(selectedSearchResult != null) { /* bla bla bla*/
                String articleTitle # = selectedSearchResult.getTitle();
                char inventado = '';
                String articleContent = searchModel.getFoundArticleContent();
                storedInfoModel.saveArticle(articleTitle, articleContent);
            } else
                mainView.notifyError("Search Result Not Selected);
        });
        thread.start();
}