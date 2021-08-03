package soccer.prjoect.request;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import soccer.prjoect.classes.SoccerInfo;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Controller
@Slf4j
public class RequestController {

    boolean isRemove(SoccerInfo temp, List<String> filters){
        boolean index = false;
        for (String filter : filters) {
            if(temp.getTitle().contains(filter))
                index=true;
        }
        //filter반복문이 끝났을시 판단을 하게된다.
        if(index==false)
            return true;
        else
            return false;
    }
    
    @RequestMapping("/")
    public String info(Model model){

        List<String> Filters = new ArrayList<>(){
            {
                add("Manchester United");
                add("Manchester City");
                add("Chelsea");
                add("Arsenal");
                add("Tottenham Hotspur");
                add("Liverpool");    // BIG 6   ++ 레스터시티 에버튼 웨스트햄 추가 예정

                add("PSG");
                add("Marseille");
                add("Lyon");
                add("Lille");
                //모나코 추가 예정

                add("Sevilla");
                add("Atletico Madrid");
                add("Barcelona");
                add("Villarreal");
                add("Real Madrid");
                // 소시에다드 베티스 추가 예정.

                add("AC Milan");
                add("Roma");
                add("Inter Milan");
                add("Atalanta");  //아탈란타 수정해야할수도
                add("Juventus");
                add("Napoli");
                // 세리에

                //독일
                add("Borussia Dortmund");
                add("Bayern Munich");  //뮌헨
                add("Leipzig");  //라이프치히 수정해야할수도
                add("Wolfsburg");  //볼프스부르크

                add("Ajax");
                add("Porto"); //벤피카예정

            }
        };
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>(); //params는 몸체부분에 해당합니다.
        HttpHeaders headers = new HttpHeaders();  // HttpHeaders라는 클래스를 통해서 요청헤더를 만듭니다.
        headers.add("x-rapidapi-key","8ef9fad904mshadf98176765adadp1153f7jsn3c8ad424db57");
        headers.add("x-rapidapi-host","free-football-soccer-videos.p.rapidapi.com");
        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(params, headers);
        // HttpEntity를 통해서 헤더부와 몸체부를 결합합니다.

        RestTemplate rt = new RestTemplate();  // RestTemplate 클래스를 통해서 요청을 진행할수있습니다.

        ResponseEntity<List<SoccerInfo>> resultlist = rt.exchange(   //exchange함수로 요청을실행하고 ResponseEntity를 반환!
                "https://free-football-soccer-videos.p.rapidapi.com/", //{요청할 서버 주소}
                HttpMethod.GET, //{요청할 방식}
                entity, // {요청할 때 보낼 데이터}
                new ParameterizedTypeReference<List<SoccerInfo>>() {}
        );
        List<SoccerInfo> result = resultlist.getBody();
        Iterator<SoccerInfo> iter = result.iterator();
        while(iter.hasNext()) {
            SoccerInfo temp = iter.next();
            if (isRemove(temp,Filters)) {
                iter.remove();
            }
        }

        model.addAttribute("results",result);
        return "index";
        /* 원래는 ResponseEntity형을 반환하게 해서 정보가 넘겨 오는거를 확인했다. 이제는 이제 이걸 모델에 담고 응답정보를 모델에 넘겨서
        *  처리를 해볼생각이다.
        * */
    }
}
