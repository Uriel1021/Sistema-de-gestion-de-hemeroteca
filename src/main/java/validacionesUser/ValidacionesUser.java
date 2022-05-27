package validacionesUser;

import java.text.Normalizer;

public class ValidacionesUser {

    private static final String ORIGINAL
            = "ÁáÉéÍíÓóÚúÑñÜü";
    private static final String REPLACEMENT
            = "AaEeIiOoUuNnUu";
    /* Este metodo quita TODOS los espacios y cambia las letras que tienen tildes pero elimina espacios entre dos nombres*/
    public String normalizeString(String str) {
        //NFKD
        //NFC
        //NFD
        //NFKC
        str = Normalizer.normalize(str, Normalizer.Form.NFKD);
        return str.replaceAll("[^a-z,^A-Z,^0-9]", "");
    }
    
    //Quita solo acentos de las letras que deben tildarse, si ingresan una que no está contemplada la ingresará tal cual
    public String quitAcent(String str) {
        if (str == null) {
            return null;
        }
        char[] array = str.toCharArray();
        for (int index = 0; index < array.length; index++) {
            int pos = ORIGINAL.indexOf(array[index]);
            if (pos > -1) {
                array[index] = REPLACEMENT.charAt(pos);
            }
        }
        return new String(array);
    }
}
