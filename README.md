# 最初に
## これは何？
大学実験で行った成果物です。  

ArduinoProcessingと以下の部品を使って新規性のあるユニークなものを作れという課題です。以下以外の持ち込み部品は使用不可。  
- Arduino(互換、Proなどはダメ)
- LED
- 抵抗
- 可変抵抗
- 加速度センサ
- 温度センサ
- フォトトランジスタ(照度センサ)
- サーボモーター
- DCモーター(途中から使えるようになった)
- スイッチ
- タクトスイッチ(途中から使えるようになった)
- スピーカ

## なんでGitHubに上げたの
このような何かを作る課題ではいつも実用性のある物を作ってきたが、何も実用性のあるものが思い浮かばなかった為。  
Arduino Proや7セグディスプレイが使えればもっとアイディアが広がったと思うんですけどね。  

## 何を作ったの？
ライントレーサーを作ろうと思ったが最初からは使えなくて、いつ入るか分からなかったので最初から使えないものは無いのも同然であるので、ライントレーサーの白黒を読み取るという所から着想を得て一次元バーコードリーダーを作りました。  

## P.S.
因みに提出したレポートは自分が書いた物を著作権侵害と言われて非常にムカついています。  
コメントにコメントを付けてやりました。  

![返却コメント](img/返却コメント.jpg)

最初は疑問系なのにその後ろで断定していることから、最初に疑問形で書いたのは建前で最初から写しただろと思っていることがこの文章から読み取れますね。  
中々ここまで人間の怒りを最高潮にすることはできませんよ、どの教授が書いたかは知りませんけど。  
エンジニアとして考えうる限り最大限の侮辱。  

## 最初に終わり
ここから提出したレポートの建前などを除いてどう実現したのかを書いていきます。  
<br>
<br>
<br>

--------------------

<br>
<br>
<br>

# 前提条件
[make_barcode](make_barcode)でバーコードを生成しました。  
A4サイズ横マックスになるようになっています。  
縦は紙がもったいないからいっぱい入るように。  
幅17cmの巨大一次元コードです。これ以上小さいと読み取れなかった。  

# ハードウェア
Arduino NanoにフォトトランジスタとLEDと読み取り開始のためのボタンのみです。  
<img alt="Arduino" src="img/Arduino.jpg" width="50%">

回路はこんな感じ  
<img alt="回路" src="img/回路.jpg" width="50%">


# LICENSE
 - barcode4j
    Apache License  
    Version 2.0, January 2004  
    http://www.apache.org/licenses/  

 - avalon-framework-api 
    Apache License  
    Version 2.0, January 2004  
    http://www.apache.org/licenses/  

 - processing
    GNU GENERAL PUBLIC LICENSE  
    Version 2, June 1991  
    https://github.com/processing/processing/blob/master/license.txt  

 - jssc
    GNU LESSER GENERAL PUBLIC LICENSE  
    Version 3, 29 June 2007  
    https://github.com/arduino/Arduino/blob/master/arduino-core/lib/jssc.LICENSE.LGPL.txt  

 - kotlin-stdlib-jdk8  
    Apache License  
    Version 2.0, January 2004  
    http://www.apache.org/licenses/  
