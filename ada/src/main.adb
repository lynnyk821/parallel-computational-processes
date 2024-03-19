with Ada.Text_IO, GNAT.Semaphores;
use Ada.Text_IO, GNAT.Semaphores;

with Ada.Containers.Indefinite_Doubly_Linked_Lists;
use Ada.Containers;

procedure Main is
   package String_Lists is new Indefinite_Doubly_Linked_Lists (String);
   use String_Lists;

   procedure Starter (Storage_Size, Item_Numbers : Integer; P_C_N : in Positive) is
      Storage : List;

      Access_Storage : Counting_Semaphore (1, Default_Ceiling);
      Full_Storage   : Counting_Semaphore (Storage_Size, Default_Ceiling);
      Empty_Storage  : Counting_Semaphore (0, Default_Ceiling);

      task type Producer;
      task type Consumer;

      type Producer_Array is array (Positive range <>) of Producer;
      type Consumer_Array is array (Positive range <>) of Consumer;

      task body Producer is
      begin
         for i in 1 .. Item_Numbers loop
            Full_Storage.Seize;
            Access_Storage.Seize;

            Storage.Append ("item " & i'Img);
            Put_Line ("Added item " & i'Img);

            Access_Storage.Release;
            Empty_Storage.Release;
            delay 0.5;
         end loop;

      end Producer;

      task body Consumer is
      begin
         for i in 1 .. Item_Numbers loop
            Empty_Storage.Seize;
            Access_Storage.Seize;

            declare
               item : String := First_Element (Storage);
            begin
               Put_Line ("Took " & item);
            end;

            Storage.Delete_First;

            Access_Storage.Release;
            Full_Storage.Release;

            delay 2.0;
         end loop;

      end Consumer;
      Producers : Producer_Array(1 .. P_C_N);
      Consumers : Consumer_Array(1 .. P_C_N);
   begin
      null;
   end Starter;
begin
   Starter (10, 5, 5);
end Main;
