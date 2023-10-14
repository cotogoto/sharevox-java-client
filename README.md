# Voicevox Synthesis Java Client

Voicevox Synthesis Java Clientは、[VOICEVOX](https://voicevox.hiroshiba.jp/)エンジンを使用してテキストから音声合成を行うためのJavaクライアントライブラリです。

## 特徴

- VOICEVOXエンジンを使用してテキストから高品質な音声合成を行います。
- Javaで簡単に統合でき、さまざまなアプリケーションに音声合成機能を追加できます。
- テキスト、スピーカー、音調のカスタマイズが可能です。

## 動作環境

- Java 8以降
- VOICEVOXエンジン（ローカルサーバー）のインストールと実行が必要です。[公式ウェブサイト](https://voicevox.hiroshiba.jp/)からダウンロードできます。

## インストール

Mavenを使用してVoicevox Synthesisライブラリをプロジェクトに追加します。

```xml
<dependency>
    <groupId>jp.livlog</groupId>
    <artifactId>voicevox-synthesis-java</artifactId>
    <version>1.0.0</version> <!-- 最新バージョンを指定 -->
</dependency>
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

このプロジェクトはMITライセンスの下で提供されています。詳細については[LICENSE](LICENSE)ファイルをご覧ください。

## お問い合わせ

ご質問や提案がある場合は、[Issues](https://github.com/blue-islands/voicevox-java-client/issues)セクションでお知らせください。コントリビューションも歓迎します。
