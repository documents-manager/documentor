package org.documentmanager.control;

import javax.enterprise.context.ApplicationScoped;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@ApplicationScoped
public class FileHasher {
  String hashStream(final InputStream data) throws NoSuchAlgorithmException, IOException {
    final var digest = MessageDigest.getInstance("SHA-256");

    // Create byte array to read data in chunks
    final var byteArray = new byte[1024];
    var bytesCount = 0;

    // Read file data and update in message digest
    while ((bytesCount = data.read(byteArray)) != -1) {
      digest.update(byteArray, 0, bytesCount);
    }

    // close the stream; We don't need it now.
    data.close();

    // Get the hash's bytes
    final byte[] bytes = digest.digest();

    final var sb = new StringBuilder();
    for (final byte aByte : bytes) {
      sb.append(Integer.toString((aByte & 0xff) + 0x100, 16).substring(1));
    }

    // return complete hash
    return sb.toString();
  }
}
