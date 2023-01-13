An Example Of Dialogs in Bot ms Azure. 

The Structure is the following:


![Dialoghi Bot tree](https://user-images.githubusercontent.com/24482406/212283579-b3d42c0c-ddf2-4970-8efe-53d6ebe9c1f9.png)



We have 3 dialogs, D1, D2, D3:
  -D1 has a WaterFall consisting of 4 steps, in order: S1, S2, S3, S4
  -D2 has a WaterFall consisting of 3 steps, in order: SS1, SS2, SS3
  -D3 has a WaterFall consisting of 2 steps, in order: SSS1, SSS2
  
 D1 is the first Dialog called.
 D2 is a sub-dialog of D1 and it is called at the S2 (step 2) of D1.
 D3 is a sub-dialog of D3 and it is called at the SS1 (step 1) of D2.
 
 
