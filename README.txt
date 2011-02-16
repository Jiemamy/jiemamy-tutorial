【前提条件】
- Java 5.0以降
- Maven 2.X
- ネットワーク環境
- チュートリアル実行用のPostgreSQLデータベース

【準備】
1. PostgreSQLを起動しておく。テーブルは無い状態にしておく。
2. database.properties_exampleを参考に、同じディレクトリにdatabase.propertiesを作成する。
3. profiles.xml_exampleを参考に、同じディレクトリにprofiles.xmlを作成する。

【手順】
1. mvn jetty:run でサーバを起動。
2. http://localhost:8080/jiemamy-tutorial/tutorial にアクセスし、テーブルが見つからないエラーを確認。
3. mvn jiemamy:execute でDBをつくってから、 mvn jetty:run し、データが表示されることを確認。
   （mvn jiemamy:execute jetty:run と、一気に指定してもよい）
4. mvn jiemamy:clean jetty:run すると、またテーブルが見つからないことを確認。
5. Jiemamy Model Editor で tutorial.jiemamy のテーブル名やカラム名をいじり、JiemamyServletも修正する。
   （さらに、お好みでデータセットも修正してもよい）
6. mvn jiemamy:execute jetty:run すると、同じようにデータが表示されることを確認。
   （データセットを修正した場合はデータも変わっていることを確認）
7. mvn jiemamy:clean で後片付けをしておく。
   