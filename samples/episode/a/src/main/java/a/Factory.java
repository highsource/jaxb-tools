package a;

public class Factory {

    public void checkEnumValues(){
        // doesn't matter what we put here, we just want to make sure the enums are being constructed correctly
        if(A2EnumType.ABC == A2EnumType.DEF){
            System.exit(1);
        }
        if(A3EnumType.START == A3EnumType.FINISH){
            System.exit(1);
        }
        if(A4EnumType.START == A4EnumType.FINISH){
            System.exit(1);
        }
    }
}
