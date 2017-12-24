package com.ucsunup.easylog.weaving.plugin

class EasyLogExtension {
    def enabled = true

    def setEnabled(boolean enabled) {
        this.enabled = enabled
    }

    def getEnabled() {
        return enabled;
    }
}
