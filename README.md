# Sharevox Synthesis Java Client

Sharevox Synthesis Java Clientは、[SHAREVOX](https://www.sharevox.app/)エンジンを使用してテキストから音声合成を行うためのJavaクライアントライブラリです。

## 特徴

- SHAREVOXエンジンを使用してテキストから高品質な音声合成を行います。
- Javaで簡単に統合でき、さまざまなアプリケーションに音声合成機能を追加できます。
- テキスト、スピーカー、音調のカスタマイズが可能です。

## 動作環境

- Java 17以降
- SHAREVOXエンジン（ローカルサーバー）のインストールと実行が必要です。[公式ウェブサイト](https://www.sharevox.app/)からダウンロードできます。

## ダウンロード
Latest Version:
[![](https://jitpack.io/v/cotogoto/sharevox-java-client.svg)](https://jitpack.io/#cotogoto/sharevox-java-client)

下記の **VERSION** キーを上記の最新バージョンに必ず置き換えてください

Maven
```xml
<dependency>
    <groupId>com.github.cotogoto</groupId>
    <artifactId>sharevox-java-client</artifactId>
    <version>VERSION</version>
</dependency>
<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>
```

## 使用法

```java
import jp.livlog.sharevox.SharevoxSynthesis;

public class Main {
    public static void main(String[] args) {
        try {
            String text = "こんにちは、SHAREVOXを試しています";
            int speaker = 1;
            boolean enableInterrogativeUpspeak = false;
            
            byte[] audioData = SharevoxSynthesis.synthesis(text, speaker, enableInterrogativeUpspeak);
            
            // audioDataを処理または保存するコードを追加
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
```

## ライセンス

このプロジェクト自体ははMITライセンスの下で提供されています。詳細については[LICENSE](LICENSE)ファイルをご覧ください。
ただし、SHAREVOXの商用利用については、別途以下の対応が必要です。

## SHAREVOXの商用利用について

SHAREVOXを商用利用する場合、クレジット表記が必要です。詳細については、[SHAREVOX利用規約](https://www.sharevox.app/terms)をご確認ください。

### クレジット表記例

```
SHAREVOX：剣崎雌雄
立ち絵：針無シ 様
```

この表記は、アプリケーションのクレジットセクションやウェブサイトのフッターなど、ユーザーが容易にアクセスできる場所に追加してください。

## お問い合わせ

ご質問や提案がある場合は、[Issues](https://github.com/blue-islands/sharevox-java-client/issues)セクションでお知らせください。コントリビューションも歓迎します。
