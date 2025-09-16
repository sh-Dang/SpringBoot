export default function PreLoader(){
    return(
        <>
            {/* Preloader */}
            <div className="preloader flex-column justify-content-center align-items-center">
                <img className="animation__shake" src="/public/dist/img/AdminLTELogo.png" alt="AdminLTELogo" height="60" width="60"/>
            </div>
        </>
    )
}