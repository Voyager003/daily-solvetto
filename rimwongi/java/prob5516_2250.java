import java.util.*;

class Solution {
    
    /** pseudo code
    1. 주어진 시작 시간과 종료 시간을 초로 변환
    2. 시작 시간과 종료 시간을 초 단위로 변경한 값을 각각 start와 end에 저장
    3. for문을 이용하여 시작 시간부터 종료 시간 직전까지 1초씩 증가하면서 반복
        3.1. 현재 초에 대한 시침, 분침, 초침의 각도로 변환
        3.2. 다음 초에 대한 시침, 분침, 초침의 각도를 변환
        3.3. 시침과 초침이 겹치는지, 분침과 초침이 겹치는지 판단
    4. 겹치는 경우에 대한 조건을 확인하여 answer를 증가시킨다.
    5. 시작 시간이 0시 또는 12시일 경우에 한 번 더 겹치는 것으로 판단  
    6. answer를 반환
    **/
    
    // 시간
    static class Time { 
        int h;
        int m;
        int s;
        
        Time(int h, int m, int s){
            this.h = h;
            this.m = m;
            this.s = s;
        }
        
        // 초로 변환된 시간으로 Time 생성
        Time(int second) {
            this.h = second / 3600;
            this.m = (second % 3600) / 60;
            this.s = (second % 3600) % 60;
        }
        
        // 각도를 담은 리스트
        List<Double> calculateDegree() {
            Double hDegree = (h % 12) * 30d + m*0.5d + s * (1/120d);
            Double mDegree = m * 6d + s * (0.1d);
            Double sDegree = s * 6d;
            
            return List.of(hDegree, mDegree, sDegree);
        }
          
        // second로 변환
        int toSecond() {
            return h * 3600 + m * 60 + s;
        }
    }
    
    public int solution(int h1, int m1, int s1, int h2, int m2, int s2) {
        int alarmCnt = 0;
        
        // 시작 시간과, 종료 시간을 초 단위로 변경
        int start = new Time(h1, m1, s1).toSecond();
        int end = new Time(h2, m2, s2).toSecond();
                
        // 시작 시간부터 1초씩 올려가며 계산(마지막 초는 제외)
        for(int i = start; i < end; i++) {
            List<Double> cnt = new Time(i).calculateDegree();
            List<Double> next = new Time(i+1).calculateDegree();
            
            boolean hMatch = isHourMatch(cnt, next);
            boolean mMatch = isMinuteMatch(cnt, next);
            
            // 초침이 분침/시침과 겹침이 발생했을 때,
            if(hMatch && mMatch) {
            	// 시침과 분침의 각도가 같다면 +1
                if(Double.compare(next.get(0), next.get(1)) == 0) {
                    alarmCnt++;
                } else {
                    // 아니라면 +2
                    alarmCnt += 2;
                }
            }
            // 둘 중 하나라도 겹치면 +1
            else if(hMatch || mMatch) {
                alarmCnt++;
            }
        }
        // 0시 또는 12시에 시작하는 경우 +1
        if(start == 0 || start == 43200) {
            alarmCnt++;
        }
        
        return alarmCnt;
    }
    
    // 시침, 초침의 겹치는 경우를 판단
    boolean isHourMatch(List<Double> cnt, List<Double> next){
        if(Double.compare(cnt.get(0),cnt.get(2)) > 0
            && Double.compare(next.get(0),next.get(2)) <= 0){
            return true;
        }
        
        // 초침이 354도에서 0도(360도)로 넘어가는 경우
        if(Double.compare(cnt.get(2), 354d) == 0 
           && Double.compare(cnt.get(0), 354d) > 0) {
            return true;
        }
        return false;
    }
    
    // 분침, 초침의 겹치는 경우를 판단
    boolean isMinuteMatch(List<Double> cnt, List<Double> next) {
        if(Double.compare(cnt.get(1), cnt.get(2)) > 0
            && Double.compare(next.get(1), next.get(2)) <= 0) {
            return true;
        }
        
        // 초침이 354도에서 0도로 넘어가는 경우
        if(Double.compare(cnt.get(2), 354d) == 0
           && Double.compare(cnt.get(1), 354d) > 0) {
            return true;
        }
        return false;
    }
}