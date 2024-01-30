# Voicevox Synthesis Java Client

Voicevox Synthesis Java Clientは、[VOICEVOX](https://voicevox.hiroshiba.jp/)エンジンを使用してテキストから音声合成を行うためのJavaクライアントライブラリです。

## 特徴

- VOICEVOXエンジンを使用してテキストから高品質な音声合成を行います。
- Javaで簡単に統合でき、さまざまなアプリケーションに音声合成機能を追加できます。
- テキスト、スピーカー、音調のカスタマイズが可能です。

## 動作環境

- Java 17以降
- VOICEVOXエンジン（ローカルサーバー）のインストールと実行が必要です。[公式ウェブサイト](https://voicevox.hiroshiba.jp/)からダウンロードできます。

## ダウンロード
Latest Version:
[![](https://jitpack.io/v/cotogoto/voicevox-java-client.svg)](https://jitpack.io/#cotogoto/voicevox-java-client)

下記の **VERSION** キーを上記の最新バージョンに必ず置き換えてください

Maven
```xml
<dependency>
    <groupId>com.github.cotogoto</groupId>
    <artifactId>voicevox-java-client</artifactId>
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
import jp.livlog.voicevox.VoicevoxSynthesis;

public class Main {
    public static void main(String[] args) {
        try {
            String text = "こんにちは、VOICEVOXを試しています";
            int speaker = 1;
            boolean enableInterrogativeUpspeak = false;
            
            byte[] audioData = VoicevoxSynthesis.synthesis(text, speaker, enableInterrogativeUpspeak);
            
            // audioDataを処理または保存するコードを追加
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
```

詳細な使用法と設定オプションについては、[ドキュメンテーション](https://github.com/yourusername/voicevox-synthesis-java/wiki)を参照してください。

## ライセンス

このプロジェクト自体ははMITライセンスの下で提供されています。詳細については[LICENSE](LICENSE)ファイルをご覧ください。
ただし、VOICEVOXの商用利用については、別途以下の対応が必要です。

## VOICEVOXの商用利用について

VOICEVOXを商用利用する場合、クレジット表記が必要です。詳細については、[VOICEVOX利用規約](https://voicevox.hiroshiba.jp/term/)をご確認ください。

### クレジット表記例

```
VOICEVOX：剣崎雌雄
立ち絵：針無シ 様
```

この表記は、アプリケーションのクレジットセクションやウェブサイトのフッターなど、ユーザーが容易にアクセスできる場所に追加してください。

## お問い合わせ

ご質問や提案がある場合は、[Issues](https://github.com/blue-islands/voicevox-java-client/issues)セクションでお知らせください。コントリビューションも歓迎します。
