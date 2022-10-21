function Convert-ResultCodeToName{
    param( [Parameter(Mandatory=$true)] [int] $ResultCode)
        $Result = $ResultCode
        switch($ResultCode)
        {
            2{
                $Result = "Succeeded"
            }3{
                $Result = "Succeeded With Errors"
            }4{
                $Result = "Failed"
            }
        }
    return $Result
}
function Get-History{

    $session = (New-Object -ComObject 'Microsoft.Update.Session')
    $history = $session.QueryHistory("",0,50) | ForEach-Object {
    $Result = Convert-ResultCodeToName -ResultCode $_.ResultCode

    $_ | Add-Member -MemberType NoteProperty -Value $Result -Name Result
    $Product = $_.Categories | Where-Object {$_.Type -eq 'Product'} | Select-Object -First 1 -ExpandProperty Name
    $_ | Add-Member -MemberType NoteProperty -Value $_.UpdateIdentity.UpdateId -Name UpdateId
    $_ | Add-Member -MemberType NoteProperty -Value $_.UpdateIdentity.RevisionNumber -Name RevisionNumber
    $_ | Add-Member -MemberType NoteProperty -Value $Product -Name Product -PassThru
    Write-Output $_
    }

    $history |
    Where-Object {![String]::IsNullOrWhiteSpace($_.title)} |
    Select-Object Result, Date, Title, SupportUrl, Product, UpdateId, RevisionNumber
}

Get-History | Format-Table