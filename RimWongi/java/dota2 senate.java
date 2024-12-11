class Solution {
    /*
    변경 사항에 대한 투표는 라운드 기반 절차
    각 라운드에서 각 상원의원은 두 가지 권리 중 하나를 행사할 수 있습니다.
    1. 한 상원의원의 권리 금지: 상원의원은 이번 라운드와 다음 라운드에서 다른 상원의원이 자신의 모든 권리를 잃게 만들 수 있습니다.
    2. 승리를 발표: 여전히 투표권이 있는 상원의원이 모두 같은 정당 출신이라는 사실을 알게 되면 승리를 선언하고 게임의 변화를 결정할 수 있습니다.
    각 상원의원을 대표하는 문자열 상원이 주어지면 파티 소속. 문자 'R'과 'D'는 Radiant 파티와 Dire 파티를 나타냅니다. 
    그런 다음 n명의 상원 의원이 있는 경우 주어진 문자열의 크기는 n이 됩니다.
    라운드 기반 절차는 주어진 순서대로 첫 번째 상원 의원부터 마지막 ​​상원 의원까지 시작됩니다. 
    이 절차는 투표가 끝날 때까지 지속됩니다. 권리를 잃은 모든 상원의원은 절차 중에 제외됩니다.
     */
    public String predictPartyVictory(String senate) {
        Deque<Integer> radiant = new ArrayDeque<>();
        Deque<Integer> dire = new ArrayDeque<>();

        for(int i=0; i<senate.length(); i++) {
            if(senate.charAt(i)=='R') {
                radiant.offer(i);
            } else {
                dire.offer(i);
            }
        }

        int n = senate.length();

        while(!radiant.isEmpty() && !dire.isEmpty()) {
            int rIdx = radiant.poll();
            int dIdx = dire.poll();

            if(rIdx < dIdx) {
                radiant.offer(rIdx + n);
            } else {
                dire.offer(dIdx + n);
            }
        }
        return radiant.isEmpty() ? "Dire" : "Radiant";
        
    }
}