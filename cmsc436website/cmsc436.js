
/*
       <div id="namesapps"></div>
    <div id="appicon"></div>
    <!---this is the website address:  https://github.com/obviousninja/SickSickDoctorIsIn/tree/master/TheDocIsIn --->
        <div id="sourcefile"><a href="TheDocIsIn.apk" download>Download link</a></div>
    <div id="apkfile"><a href="TheDocIsIn.zip" download>Download link</a></div>
    
    <div id="screenshot"></div>
    <div id="description"></div>
    <div id="test"></div>
    <div id="screencast"></div>
 
 */

$(document).ready(function(){
    $("body").css({"backgroundColor":"#CCA352"});
    $("#appicon").css({"border":"solid", "borderRadius":"10px", "top":"20px", "left":"20px", "height":"100px", "width":"100px", "position":"absolute"});
    $("#appicon").append("<img src='doctor.png' alt='doctor'>");
    $("#namesapps").css({"border":"solid", "borderRadius":"10px", "top":"20px", "left":"125px", "height": "100px", "width":"700px", "position":"absolute"});
    $("#namesapps").append("<br>Doctor is IN");
    $("#namesapps").css({"font": "italic bold 40px arial,serif", "textAlign":"center"});
    $("#sourcefile").css({"border":"solid", "borderRadius":"10px", "top":"125px", "left":"20px", "height": "100px", "width":"350px", "position":"absolute"});
    
    $("#sourcefile").append("<a href='TheDocIsIn.zip' download>SOURCE FILE DOWNLOAD</a>");
    $("#sourcefile").css({"font": "italic bold 30px arial,serif", "textAlign":"center"});    

    $("#apkfile").css({"border":"solid", "borderRadius":"10px", "top":"125px", "left":"375px", "height": "100px", "width":"450px", "position":"absolute"});
    $("#apkfile").append("<a href='TheDocIsIn.apk' download>APK DOWNLOAD</a>");
    $("#apkfile").css({"font": "italic bold 30px arial,serif", "textAlign":"center"});
    
    $("#description").css({"border":"solid", "borderRadius":"10px", "top":"230px", "left":"20px", "height": "370px", "width":"805px", "position":"absolute"});
    $("#description").append("<h3>Description</h3><b>Minimum Level SDK: </b> <span><i>11.</i></span> <b>Targeted Level SDK: </b><span><i>21.</i></span>");
    $("#description").append("<b>Development Enviroment</b> <span><i>Eclipse</i></span>");
    

    var text1 = "<div>The Doctor Is In is a question and answer app. When a user signs up, he or she</div>";
var text2 = "<div>will be granted an initial 20 coins. Asking a question costs 5 coins,</div>";
var text3 = "<div>but answering a question earns 3 coins. Multiple users can answer any question</div>";
var text4 = "<div>and anyone who views a question can vote any answer positively or negatively. The highest</div>";
var text5 = "<div>voted answers are pushed to the top of the answer list. After a user answers a question,</div>";
var text6 = "<div>the asker can decide if that answer is satisfactory. If the answer is satisfactory, the asker</div>";
var text7 = "<div>can choose that question as ''the best answer'' and close the question so no more answers can be given.</div>";
var text8 = "<div>When a user asks a question, he or she is asked what category</div>";
var text9 = "<div>they want their question to be. The home screen of our app has all of the categories as buttons and</div>";
var text10 = "<div>clicking each category button takes you to all of the questions within that category.</div>"
var text11 = "<div></div>";
var text12 = "<div><b>Testing Done:</b><br><p>We did a few cases of user testing and returned no problems.</p></div>";


    $("#description").append("<p></p>",text1, text2, text3, text4, text5, text6, text7, text8, text9, text10, text11, text12 );
    
    //screenshot1
    $("#screenshot1").append("<img id='sample1' src='sample.png' alt='doctor' height='100' width='350' >");
    $("#screenshot1").css({"border":"solid", "borderRadius":"10px", "top":"605px", "left":"20px", "height": "100px", "width":"350px", "position":"absolute"});  
    
    
    //screenshot2
    $("#screenshot2").append("<img id='sample2' src='sample.png' alt='doctor' height='100' width='350' >");
    $("#screenshot2").css({"border":"solid", "borderRadius":"10px", "top":"605px", "left":"375px", "height": "100px", "width":"450px", "position":"absolute"});
    
    //screencast
    $("#screencast").append("<video width='320' height='220' controls><source src='bunny.mp4' type='video/mp4'></video>");
    $("#screencast").css({"border":"solid", "borderRadius":"10px", "top":"715px", "left":"20px", "height": "230px", "width":"585px", "position":"absolute","paddingLeft":"220px"});
    

    
    var screenheight = (screen.height-150) + "px";
    var screenwidth = (screen.width-20) + "px";
    $("#blownBackground").css({"opacity": "0.3", "top": "10px", "left": "10px", "height": screenheight, "width": screenwidth, "zindex": "100", "backgroundColor":"black", "position":"absolute"});
    
    
    //first image
    $("#blownContent1").append("<img src='sample.png' alt='doctor' height='800' width = '600'>");
    $("#blownContent1").css({"zindex":"200", "opacity":"1", "position":"absolute", "left":"640px", "top":"100px" });
    
    //second image
    $("#blownContent2").append("<img src='sample.png' alt='doctor' height='800' width = '600'>");
    $("#blownContent2").css({"zindex":"200", "opacity":"1", "position":"absolute", "left":"640px", "top":"100px" });
        
    
    $("#blownContent1").hide();
    $("#blownContent2").hide();
    $("#blownBackground").hide();  //correct code that initially hides the background opacityBackground
    
    
    $("#sample1").click(function(){
       $("#blownBackground").fadeIn();
       $("#blownContent1").fadeIn();

       $("#blownBackground").click(function(){
         $("#blownContent1").fadeOut();
         $("#blownBackground").fadeOut();
        });
    });
    
    $("#sample2").click(function(){
       $("#blownBackground").fadeIn();
       $("#blownContent2").fadeIn();

       $("#blownBackground").click(function(){
         $("#blownContent2").fadeOut();
         $("#blownBackground").fadeOut();
        });
    });
    
    
    });