class Solution {
    public int numRescueBoats(int[] people, int limit) {
  Arrays.sort(people);
        //3,2,2,1  --> 1,2,2,3
        // In pairing problems, the heaviest element always moves; only the lightest is conditional
        //As pairing ham sirf mote aur patle ka krengey tabhi minimum number of boats lagengi agar patle
        //patle ka krengey to number of boats bad jayengi
        int i=0;
        int j=people.length-1;
        int count=0;
        while(j>=i){
            //final base condition
            if(i==j && people[i]<=limit){
                count++;
                break;
            }
             if(people[i]+people[j]<=limit){
                count++;
                i++;
                j--;
            }
           else{
            // this is the case when the heavisest person is sent alone
            j--;
            count++;
           }
            
         
            
        }
        return count;

        }

        
    }
