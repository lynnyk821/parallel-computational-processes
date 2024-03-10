with Ada.Text_IO; use Ada.Text_IO;

procedure Main is
   Arr_Len : Integer := 10;
   Number_Of_Threads : Integer := 5;

   Arr : array(1..Arr_Len) of Integer;

   type Min_With_Index is record
      Value : Integer;
      Index : Integer;
   end record;

   procedure Init_Arr is
   begin
      for I in 1..Arr_Len loop
         if I = Arr_Len / 2 then
            Arr(I) := -1;
         else
            Arr(I) := I;
         end if;
      end loop;
   end Init_Arr;

   function Part_Min(Start_Index, Finish_Index : in Integer) return Min_With_Index is
      Min : Min_With_Index := (Value => Arr(Start_Index), Index => Start_Index);
   begin
      for I in Start_Index + 1 .. Finish_Index loop
         if Arr(I) < Min.Value then
            Min := (Value => Arr(I), Index => I);
         end if;
      end loop;
      return Min;
   end Part_Min;

   task type Starter_Thread is
      entry Start(Start_Index, Finish_Index : in Integer);
      entry Get_Min(Min_Value : out Min_With_Index);
   end Starter_Thread;

 task body Starter_Thread is
      Min : Min_With_Index := (Value => 0, Index => 0);
      SIndex, FIndex : Integer;
   begin
      accept Start(Start_index, Finish_index : in Integer) do
         SIndex := Start_index;
         FIndex := Finish_index;
      end Start;

      Min := Part_Min(SIndex, FIndex);
      accept Get_Min(Min_Value : out Min_With_Index) do
         Min_Value := Min;
      end Get_Min;
   end Starter_Thread;


   procedure Lab2 is
      Overall_Min : Min_With_Index;
      Thread : array(1..Number_Of_Threads) of Starter_Thread;
      Min_Values : array(1..Number_Of_Threads) of Min_With_Index;
   begin
      Init_Arr;

      for I in 1..Number_Of_Threads loop
         Thread(I).Start((I - 1) * Arr_Len / Number_Of_Threads + 1, I * Arr_Len / Number_Of_Threads);
      end loop;

      for I in 1..Number_Of_Threads loop
         Thread(I).Get_Min(Min_Values(I));
         Put(Min_Values(I).Value'Img & " ");
      end loop;
      Put_Line("");

      Overall_Min := Min_Values(1);
      for I in 2..Number_Of_Threads loop
         if Min_Values(I).Value < Overall_Min.Value then
            Overall_Min := Min_Values(I);
         end if;
      end loop;

      Put_Line("Overall minimum: " & Overall_Min.Value'Img & " at index " & Overall_Min.Index'Img);
   end Lab2;
begin
   Lab2;
end Main;
