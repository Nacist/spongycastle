package org.spongycastle.jcajce.provider.digest;

import org.spongycastle.asn1.iana.IANAObjectIdentifiers;
import org.spongycastle.crypto.CipherKeyGenerator;
import org.spongycastle.crypto.digests.TigerDigest;
import org.spongycastle.crypto.macs.HMac;
import org.spongycastle.jcajce.provider.config.ConfigurableProvider;
import org.spongycastle.jcajce.provider.symmetric.util.BaseKeyGenerator;
import org.spongycastle.jce.provider.JCEMac;

public class Tiger
{
    static public class Digest
        extends BCMessageDigest
        implements Cloneable
    {
        public Digest()
        {
            super(new TigerDigest());
        }

        public Object clone()
            throws CloneNotSupportedException
        {
            Digest d = (Digest)super.clone();
            d.digest = new TigerDigest((TigerDigest)digest);

            return d;
        }
    }

    /**
     * Tiger HMac
     */
    public static class HashMac
        extends JCEMac
    {
        public HashMac()
        {
            super(new HMac(new TigerDigest()));
        }
    }

    public static class KeyGenerator
        extends BaseKeyGenerator
    {
        public KeyGenerator()
        {
            super("HMACTIGER", 192, new CipherKeyGenerator());
        }
    }

    public static class Mappings
        extends DigestAlgorithmProvider
    {
        private static final String PREFIX = Tiger.class.getName();

        public Mappings()
        {
        }

        public void configure(ConfigurableProvider provider)
        {
            provider.addAlgorithm("MessageDigest.TIGER", PREFIX + "$Digest");
            provider.addAlgorithm("MessageDigest.Tiger", PREFIX + "$Digest"); // JDK 1.1.

            addHMACAlgorithm(provider, "TIGER", PREFIX + "$HashMac", PREFIX + "$KeyGenerator");
            addHMACAlias(provider, "TIGER", IANAObjectIdentifiers.hmacTIGER);
        }
    }
}
