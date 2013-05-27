/*
 * LICENSE
 *
 * "THE BEER-WARE LICENSE" (Revision 43):
 * "Sven Strittmatter" <weltraumschaf@googlemail.com> wrote this file.
 * As long as you retain this notice you can do whatever you want with
 * this stuff. If we meet some day, and you think this stuff is worth it,
 * you can buy me a non alcohol-free beer in return.
 *
 * Copyright (C) 2012 "Sven Strittmatter" <weltraumschaf@googlemail.com>
 */
package org.lafayette.server.nonce;

import com.google.common.collect.Lists;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.List;
import org.apache.commons.codec.digest.DigestUtils;

/**
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
abstract class BaseNonce implements Nonce {

    static final String DEFAULT_SALT =
            "Shiun4Wuahg7Peijio7VipieUoquie8yiveuKae4Ailae2FieeW7aeheipe1eNg2"
            + "eeH5reerXeig7ahtAiXiequ5De0aiJagEocho8heaekaiTh5ro3OokaeJu0thiom"
            + "iedait4Diew8Uz2xQuophi6AIezohch5eih8Ohciixee1Oodchui4SeiAiF1kequ"
            + "YuM1hai7wohZ6daieighim6IThi9quohFai4haideing1iFuyahJ9ahtmoh3Ieb6"
            + "tae8eeGhuung5Beiza3Bohcoko4dai5UQuoh5Mohxia5Soo9LeuBae8doum2Ahng"
            + "Saem8iesvieTha7Iaix3wuNgieL4paeboiFaek2aGoo6aithdoor7OocieWaib0u"
            + "geiP7ba1Sah2eeLoij2caa7Hal9soVohpeer6LieohRee9moZohtaeb3Quoo0mah"
            + "aiph3Pohyaeph4OoXoyiGie2ohl2Nae2Ew2yoo2uohshoo6Ithie7OQuooph9ahP"
            + "jaid3Phoaef3AichUiG5eiw6noo3xohNDai0ieQugo4uiMahfoo0Iem4Phoo4sie"
            + "Thaijo3nAip0ahPiReepah5yAesh8ShoiNg2poiLoQuaex0oUica2raiOhchei2I"
            + "eefieQu1aiS4aqueohWeaxo2Eechee7UquaShi4OThee6einGo8epoh3Vei3Am0K"
            + "aeghoo3Riehaih5Egahph2OhthaeS4meEkasha8oEimeeph7poh2IeghkiH2usep"
            + "OGah1ooMAeg4Wo0aAhkood1PJoot4ae9iCh8ash5Ohteiv9wAhtae8lazohFae6d"
            + "zae3DeijEichee6goKoo3ImaquaDahk5FaiGhae0kah7OhF6chogh1CoCuVu0zeV"
            + "oH5queuneaj2PhaeohS5shifPhor2iuxahS6Dee0ohc8aiY3Ae5eaghoaeWi8vei"
            + "eiRei9eeiehai3EbSo0iphaiAing1aiTEzai2kaireiDah8aui4aiCuoquo1Thei"
            + "lu5eu3aSShoo6giueNies8Umaiy4ooChfieKei8eKowiegi1yaeR1VaemeZie1te"
            + "Av0ievohauBo5iedphiZiex2theib8OutaiG8cheieShe1chaibo7UM0Coo6Ahg9"
            + "eiRei0fuYeix7iYaUequ6weeSe0oqu4Cneeb1ZiexaeV0naisee2AaGhOeh4sis7"
            + "Eith6gieeiCh9chiWei2wu2rauxeiD5equohGa6eRae2aef1oe4ahPohLoh5chie";
    /**
     * Default digest algorithm.
     */
    static final Nonce.DigestAlgorithm DEFAULT_DIGEST = Nonce.DigestAlgorithm.SHA1;
    /**
     * Default size for seed generator.
     */
    private static final int RANDOM_SEED_SIZE = 1024;
    /**
     * Bits per byte.
     */
    private static final int BITS_PER_BYTE = 8;
    /**
     * Liste of generated nonce strings.
     */
    private final List<String> usedNones = Lists.newArrayList();
    /**
     * Used salt.
     */
    private final String salt;
    /**
     * Used random number generator.
     *
     * Lazy computed.
     */
    private final SecureRandom random;
    /**
     * Used digest algorithm.
     */
    private final Nonce.DigestAlgorithm digester;
    /**
     * Seeder.
     */
    private final SecureRandom seeder;

    /**
     * Default constructor.
     *
     * @param algorithm Used nonce calculating algorithm.
     * @param salt Used hash salt.
     * @param digest Used hashing algorithm.
     * @throws NoSuchAlgorithmException On unsupported algorithm.
     */
    public BaseNonce(final Nonce.RandonAlgorithm algorithm, final String salt, final Nonce.DigestAlgorithm digest)
            throws NoSuchAlgorithmException {
        super();
        this.salt = salt;

        seeder = SecureRandom.getInstance(algorithm.getName());
        random = SecureRandom.getInstance(algorithm.getName());
        random.setSeed(generateSeed(RANDOM_SEED_SIZE));
        this.digester = digest;
    }

    /**
     * Get he number generator.
     *
     * @return Return secure random object.
     */
    protected SecureRandom getRandom() {
        return random;
    }

    /**
     * Whether a nonce string was already generated.
     *
     * @param nonce Nonce to check.
     * @return True if given nonce string already generated, otherwise false.
     */
    boolean allreadyUsed(final String nonce) {
        return usedNones.contains(nonce);
    }

    @Override
    public String getNext() {
        String nonce;

        do {
            nonce = calculateNonce();

            if (!usedNones.contains(nonce)) {
                usedNones.add(nonce);
                break;
            }
        } while (true);

        return nonce;
    }

    /**
     * Get the salt string.
     *
     * @return Sal string.
     */
    String getSalt() {
        return salt;
    }

    /**
     * Get the digester.
     *
     * @param plaintext plain text to hash
     * @return hashed plain text
     */
    protected String digest(final String plaintext) {
        switch (digester) {
            case MD5:
                return DigestUtils.md5Hex(plaintext);
            case SHA1:
                return DigestUtils.sha1Hex(plaintext);
            default:
                throw new IllegalArgumentException(String.format("Unsupported algorithm '%s'!", digester));
        }
    }

    /**
     * Generates random seed.
     *
     * @param size Seed size in bits.
     * @return Byte array with random seed.
     */
    private byte[] generateSeed(final int size) {
        final byte[] bytes = new byte[size / BITS_PER_BYTE];
        seeder.nextBytes(bytes);
        return seeder.generateSeed(size);
    }

    @Override
    public String calculateNonce() {
        final Long nonceVal = Math.abs(getRandom().nextLong());
        final StringBuilder nonceString = new StringBuilder();
        nonceString.append(getSalt()).append(Long.toHexString(nonceVal));
        return digest(nonceString.toString());
    }

}
