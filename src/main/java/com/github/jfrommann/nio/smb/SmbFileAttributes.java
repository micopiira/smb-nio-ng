package com.github.jfrommann.nio.smb;

import jcifs.smb.SmbFile;

import java.io.IOException;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.util.concurrent.TimeUnit;

public final class SmbFileAttributes implements BasicFileAttributes {

    /**
     * Unix timestamp of the creation date.
     */
    private final long created;

    /**
     * Unix timestamp of the modification date.
     */
    private final long modified;

    /**
     * Content length of the file.
     */
    private final long length;

    /**
     * Unique code used to identify the file.
     */
    private final int code;

    private final boolean isDirectory;

    private final boolean isRegularFile;

    /**
     * Public default constructor for {@link SmbFileAttributes}.
     *
     * @param path {@link SmbPath} for which to create {@link SmbFileAttributeView}.
     * @throws IOException If something goes wrong while accessing the file.
     */
    public SmbFileAttributes(SmbPath path) throws IOException {
        this(path.getSmbFile());
    }

    /**
     * Internal constructor for {@link SmbFileAttributes}.
     *
     * @param file {@link SmbFile} for which to create the attributes.
     * @throws IOException If something goes wrong while accessing the file.
     */
    SmbFileAttributes(SmbFile file) throws IOException {
        this.created = file.createTime();
        this.modified = file.lastModified();
        this.length = file.length();
        this.code = file.hashCode();
        this.isDirectory = file.isDirectory();
        this.isRegularFile = file.isFile();
    }

    @Override
    public FileTime lastModifiedTime() {
        return FileTime.from(this.modified, TimeUnit.MILLISECONDS);
    }

    @Override
    public FileTime lastAccessTime() {
        return FileTime.from(0L, TimeUnit.MILLISECONDS);
    }

    @Override
    public FileTime creationTime() {
        return FileTime.from(this.created, TimeUnit.MILLISECONDS);
    }

    @Override
    public boolean isRegularFile() {
        return this.isRegularFile;
    }

    @Override
    public boolean isDirectory() {
        return this.isDirectory;
    }

    @Override
    public boolean isSymbolicLink() {
        return false;
    }

    @Override
    public boolean isOther() {
        return false;
    }

    @Override
    public long size() {
        return this.length;
    }

    @Override
    public Object fileKey() {
        return this.code;
    }
}
