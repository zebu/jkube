/**
 * Copyright (c) 2019 Red Hat, Inc.
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at:
 *
 *     https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Red Hat, Inc. - initial API and implementation
 */
package org.eclipse.jkube.kit.config.image.build;

import java.io.Serializable;

import org.eclipse.jkube.kit.common.JKubeProjectAssembly;

public class AssemblyConfiguration implements Serializable {

    /**
     * New replacement for base directory which better reflects its
     * purpose
     */
    private String targetDir;

    /**
     * Name of the assembly which is used also as name of the archive
     * which is created and has to be used when providing an own Dockerfile
     */
    private String name = "maven";

    private String descriptor;

    private String descriptorRef;

    // use 'exportTargetDir' instead
    @Deprecated
    private Boolean exportBasedir;

    @Deprecated
    private String dockerFileDir;

    private Boolean ignorePermissions;

    /**
     * Whether the target directory should be
     * exported.
     *
     */
    private Boolean exportTargetDir;

    private PermissionMode permissions;

    private AssemblyMode mode;

    private String user;

    private String tarLongFileMode;

    /**
     * Assembly defined inline in the pom.xml
     */
    private JKubeProjectAssembly inline;

    public Boolean getExportTargetDir() {
        return exportTargetDir;
    }

    public Boolean exportTargetDir() {
        if (exportTargetDir != null) {
            return exportTargetDir;
        } else if (exportBasedir != null) {
            return exportBasedir;
        } else {
            return null;
        }
    }

    public String getDockerFileDir() {
        return dockerFileDir;
    }

    public String getTargetDir() {
        if (targetDir != null) {
            return targetDir;
        } else {
            return "/" + getName();
        }
    }

    public String getDescriptor() {
        return descriptor;
    }

    public String getDescriptorRef() {
        return descriptorRef;
    }

    public String getUser() {
        return user;
    }

    public AssemblyMode getMode() {
        return mode != null ? mode : AssemblyMode.dir;
    }

    public String getTarLongFileMode() {
        return tarLongFileMode;
    }

    public String getModeRaw() {
        return mode != null ? mode.name() : null;
    }

    public String getName() {
        return name;
    }

    @Deprecated
    public Boolean getIgnorePermissions() {
        return ignorePermissions;
    }

    public PermissionMode getPermissions() {
        return permissions != null ? permissions : PermissionMode.keep;
    }

    public String getPermissionsRaw() {
        return permissions != null ? permissions.name() : null;
    }


    public JKubeProjectAssembly getInline() {
        return inline;
    }

    public static class Builder {

        protected AssemblyConfiguration config;

        public Builder() {
            config = new AssemblyConfiguration();
        }

        private boolean isEmpty = true;

        public AssemblyConfiguration build() {
            return isEmpty ? null : config;
        }

        public Builder exportTargetDir(Boolean exportTargetDir) {
            config.exportTargetDir = exportTargetDir;
            return this;
        }

        public Builder targetDir(String targetDir) {
            config.targetDir = set(targetDir);
            return this;
        }

        public Builder descriptor(String descriptorFile) {
            config.descriptor = set(descriptorFile);
            return this;
        }

        public Builder descriptorRef(String descriptorRef) {
            config.descriptorRef = set(descriptorRef);
            return this;
        }

        public Builder permissions(String permissions) {
            if (permissions != null) {
                config.permissions = PermissionMode.valueOf(permissions.toLowerCase());
                isEmpty = false;
            }
            return this;
        }

        public Builder user(String user) {
            config.user = set(user);
            return this;
        }

        public Builder mode(String mode) {
            if (mode != null) {
                config.mode = AssemblyMode.valueOf(mode.toLowerCase());
                isEmpty = false;
            }
            return this;
        }

        public Builder tarLongFileMode(String tarLongFileMode) {
            config.tarLongFileMode = set(tarLongFileMode);
            return this;
        }

        public Builder dockerFileDir(String dockerFileDir) {
            config.dockerFileDir = set(dockerFileDir);
            return this;
        }

        public Builder exportBasedir(Boolean export) {
            config.exportBasedir = set(export);
            return this;
        }

        @Deprecated
        public Builder ignorePermissions(Boolean ignorePermissions) {
            config.ignorePermissions = set(ignorePermissions);
            return this;
        }

        public Builder assemblyDef(JKubeProjectAssembly descriptor) {
            config.inline = set(descriptor);
            return this;
        }

        protected <T> T set(T prop) {
            if (prop != null) {
                isEmpty = false;
            }
            return prop;
        }
    }

    public enum PermissionMode {

        /**
         * Auto detect permission mode
         */
        auto,

        /**
         * Make everything executable
         */
        exec,

        /**
         * Leave all as it is
         */
        keep,

        /**
         * Ignore permission when using an assembly mode of "dir"
         */
        ignore
    }
}
