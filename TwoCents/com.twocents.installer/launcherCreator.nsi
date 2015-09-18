; Java Launcher with automatic JRE installation
;-----------------------------------------------

# Included files
!include Sections.nsh
!include MUI2.nsh

# MUI Symbol Definitions
!define MUI_ICON "work/Tc_logo_01.ico"
!define MUI_FINISHPAGE_NOAUTOCLOSE
!define MUI_STARTMENUPAGE_REGISTRY_ROOT HKLM
!define MUI_STARTMENUPAGE_NODISABLE
!define MUI_STARTMENUPAGE_REGISTRY_KEY ${REGKEY}
!define MUI_STARTMENUPAGE_REGISTRY_VALUENAME StartMenuGroup
!define MUI_STARTMENUPAGE_DEFAULTFOLDER TwoCents
!define MUI_UNICON "${NSISDIR}\Contrib\Graphics\Icons\modern-uninstall-colorful.ico"
!define MUI_UNFINISHPAGE_NOAUTOCLOSE
!define COMPANY "Tio Soros"
!define URL www.twocents.com.br


# Installer languages
!insertmacro MUI_LANGUAGE PortugueseBR

 
Name "Java Launcher"
Caption "Java Launcher"
; Icon "Java Launcher.ico"
OutFile "TwoCents.exe"

 
# Installer attributes
OutFile TwoCents.exe
InstallDir $PROGRAMFILES\TwoCents
CRCCheck on
XPStyle on
ShowInstDetails show
VIProductVersion 0.1.0.0
VIAddVersionKey /LANG=${LANG_PORTUGUESEBR} ProductName TwoCents
VIAddVersionKey /LANG=${LANG_PORTUGUESEBR} ProductVersion "${VERSION}"
VIAddVersionKey /LANG=${LANG_PORTUGUESEBR} CompanyName "${COMPANY}"
VIAddVersionKey /LANG=${LANG_PORTUGUESEBR} CompanyWebsite "${URL}"
VIAddVersionKey /LANG=${LANG_PORTUGUESEBR} FileVersion "${VERSION}"
VIAddVersionKey /LANG=${LANG_PORTUGUESEBR} FileDescription ""
VIAddVersionKey /LANG=${LANG_PORTUGUESEBR} LegalCopyright ""


!define CLASSPATH "com.twocents.main.jar"
!define CLASS "com.twocents.main.Main"
!define PRODUCT_NAME "TwoCents"
 
; Definitions for Java 6.0
!define JRE_VERSION "6.0"
!define JRE_URL "http://javadl.sun.com/webapps/download/AutoDL?BundleId=48343"
;!define JRE_VERSION "5.0"
;!define JRE_URL "http://javadl.sun.com/webapps/download/AutoDL?BundleId=22933&/jre-1_5_0_16-windows-i586-p.exe"
 
; use javaw.exe to avoid dosbox.
; use java.exe to keep stdout/stderr
!define JAVAEXE "javaw.exe"
 
RequestExecutionLevel user
SilentInstall silent
AutoCloseWindow true
ShowInstDetails nevershow
 
!include "FileFunc.nsh"
!insertmacro GetFileVersion
!insertmacro GetParameters
!include "WordFunc.nsh"
!insertmacro VersionCompare
 
Section ""
  Call GetJRE
  Pop $R0
 
  ; change for your purpose (-jar etc.)
  ${GetParameters} $1
  StrCpy $0 '"$R0" -classpath "${CLASSPATH}" ${CLASS} $1'
 
  SetOutPath $EXEDIR
  Exec $0
SectionEnd
 
;  returns the full path of a valid java.exe
;  looks in:
;  1 - .\jre directory (JRE Installed with application)
;  2 - JAVA_HOME environment variable
;  3 - the registry
;  4 - hopes it is in current dir or PATH
Function GetJRE
    Push $R0
    Push $R1
    Push $2
 
  ; 1) Check local JRE
  CheckLocal:
    ClearErrors
    StrCpy $R0 "$EXEDIR\jre\bin\${JAVAEXE}"
    IfFileExists $R0 JreFound
 
  ; 2) Check for JAVA_HOME
  CheckJavaHome:
    ClearErrors
    ReadEnvStr $R0 "JAVA_HOME"
    StrCpy $R0 "$R0\bin\${JAVAEXE}"
    IfErrors CheckRegistry     
    IfFileExists $R0 0 CheckRegistry
    Call CheckJREVersion
    IfErrors CheckRegistry JreFound
 
  ; 3) Check for registry
  CheckRegistry:
    ClearErrors
    ReadRegStr $R1 HKLM "SOFTWARE\JavaSoft\Java Runtime Environment" "CurrentVersion"
    ReadRegStr $R0 HKLM "SOFTWARE\JavaSoft\Java Runtime Environment\$R1" "JavaHome"
    StrCpy $R0 "$R0\bin\${JAVAEXE}"
    IfErrors DownloadJRE
    IfFileExists $R0 0 DownloadJRE
    Call CheckJREVersion
    IfErrors DownloadJRE JreFound
 
  DownloadJRE:
    ; Call ElevateToAdmin
    MessageBox MB_ICONINFORMATION|MB_YESNO "O ${PRODUCT_NAME} requer o Java Runtime Environment ${JRE_VERSION}. Deseja que o download deste software será executado automaticamente?" IDYES true IDNO false
    true:
        Push "${JRE_URL}"
        Call openLinkNewWindow
    false:
        Quit
    
    ;StrCpy $2 "$TEMP\Java Runtime Environment.exe"
    ;nsisdl::download /TIMEOUT=30000 ${JRE_URL} $2
    ;Pop $R0 ;Get the return value
    ;StrCmp $R0 "success" +3
    ;  MessageBox MB_ICONSTOP "Download failed: $R0"
    ;  Abort
    ;ExecWait $2
    ;Delete $2
 
    ;ReadRegStr $R1 HKLM "SOFTWARE\JavaSoft\Java Runtime Environment" "CurrentVersion"
    ;ReadRegStr $R0 HKLM "SOFTWARE\JavaSoft\Java Runtime Environment\$R1" "JavaHome"
    ;StrCpy $R0 "$R0\bin\${JAVAEXE}"
    ;IfFileExists $R0 0 GoodLuck
    ;Call CheckJREVersion
    IfErrors GoodLuck JreFound
 
  ; 4) wishing you good luck
  GoodLuck:
    StrCpy $R0 "${JAVAEXE}"
    ; MessageBox MB_ICONSTOP "Cannot find appropriate Java Runtime Environment."
    ; Abort
 
  JreFound:
    Pop $2
    Pop $R1
    Exch $R0
FunctionEnd
 
; Pass the "javaw.exe" path by $R0
Function CheckJREVersion
    Push $R1
 
    ; Get the file version of javaw.exe
    ${GetFileVersion} $R0 $R1
    ${VersionCompare} ${JRE_VERSION} $R1 $R1
 
    ; Check whether $R1 != "1"
    ClearErrors
    StrCmp $R1 "1" 0 CheckDone
    SetErrors
 
  CheckDone:
    Pop $R1
FunctionEnd
 

Function openLinkNewWindow
  Push $3
  Exch
  Push $2
  Exch
  Push $1
  Exch
  Push $0
  Exch
 
  ReadRegStr $0 HKCR "http\shell\open\command" ""
# Get browser path
    DetailPrint $0
  StrCpy $2 '"'
  StrCpy $1 $0 1
  StrCmp $1 $2 +2 # if path is not enclosed in " look for space as final char
    StrCpy $2 ' '
  StrCpy $3 1
  loop:
    StrCpy $1 $0 1 $3
    DetailPrint $1
    StrCmp $1 $2 found
    StrCmp $1 "" found
    IntOp $3 $3 + 1
    Goto loop
 
  found:
    StrCpy $1 $0 $3
    StrCmp $2 " " +2
      StrCpy $1 '$1"'
 
  Pop $0
  Exec '$1 $0'
  Pop $0
  Pop $1
  Pop $2
  Pop $3
FunctionEnd