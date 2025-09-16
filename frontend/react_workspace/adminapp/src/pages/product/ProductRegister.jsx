export default function ProductRegister(){
    return(
        <>
                    {/* <!-- general form elements --> */}
            <div className="card card-primary">
              <div className="card-header">
                <h3 className="card-title">상품 등록 폼</h3>
              </div>
              {/* <!-- /.card-header --> */}
              {/* <!-- form start --> */}
              <form>
                <div className="card-body">
                  <div className="form-group">
                    <label htmlFor="exampleInputEmail1">상품명</label>
                    <input type="email" className="form-control" id="exampleInputEmail1" placeholder="상품명을 입력하세요"/>
                  </div>
                  <div className="form-group">
                    <label for="exampleInputFile">상품 정보</label>
                    <div className="input-group">
                      <div className="custom-file">
                        <input type="file" className="custom-file-input" id="exampleInputFile"/>
                      </div>
                    </div>
                  </div>
                  <div className="form-check">
                    <input type="checkbox" className="form-check-input" id="exampleCheck1"/>
                    <label className="form-check-label" for="exampleCheck1">Check me out</label>
                  </div>
                </div>
                {/* <!-- /.card-body --> */}

                <div className="card-footer">
                  <button type="submit" className="btn btn-primary">Submit</button>
                </div>
              </form>
            </div>
            {/* <!-- /.card --> */}
            </>
    )
}