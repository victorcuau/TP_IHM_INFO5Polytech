let re_matrix = /^matrix\((.*), (.*), (.*), (.*), (.*), (.*)\)$/;

let svg = document.createElementNS("http://www.w3.org/2000/svg", "svg");
let idM	= svg.createSVGMatrix();
idM.a=1; idM.b=0; idM.c=0; idM.d=1; idM.e=0; idM.f=0;

//______________________________________________________________________________________________________________________
export let setMatrixCoordToElement =    ( element: HTMLElement
                                        , a : number
                                        , b : number
                                        , c : number
                                        , d : number
                                        , e : number
                                        , f : number
                                        ) => {
    element.style.transform = "matrix(" + a +"," + b +"," + c +"," + d +"," + e +"," + f +")";
};

//______________________________________________________________________________________________________________________
export let setMatrixToElement = (element: HTMLElement, M: SVGMatrix) => {
    setMatrixCoordToElement(element, M.a, M.b, M.c, M.d, M.e, M.f);
};

//______________________________________________________________________________________________________________________
export let getMatrixFromString = (str: string) : SVGMatrix => {
    let res		= re_matrix.exec( str )
      , matrix	= svg.createSVGMatrix()
      ;
    matrix.a = parseFloat(res[1]) || 1;
    matrix.b = parseFloat(res[2]) || 0;
    matrix.c = parseFloat(res[3]) || 0;
    matrix.d = parseFloat(res[4]) || 1;
    matrix.e = parseFloat(res[5]) || 0;
    matrix.f = parseFloat(res[6]) || 0;

    return matrix;
};

//______________________________________________________________________________________________________________________
export let getPoint = (x: number, y: number) : SVGPoint => {
    let point = svg.createSVGPoint();
    point.x = x || 0;
    point.y = y || 0;
    return point;
};

//______________________________________________________________________________________________________________________
export let getMatrixFromElement = (element: Element) : SVGMatrix => {
	return getMatrixFromString( window.getComputedStyle(element).transform || "matrix(1,1,1,1,1,1)" );
};

//______________________________________________________________________________________________________________________
export let drag =       ( element               : HTMLElement
                        , originalMatrix        : SVGMatrix
                        , Pt_coord_element      : SVGPoint
                        , Pt_coord_parent       : SVGPoint
                        ) => {
    // Calculate variables e and f of the new matrix, after translation
    originalMatrix.e = Pt_coord_parent.x - originalMatrix.a * Pt_coord_element.x - originalMatrix.c * Pt_coord_element.y;
    originalMatrix.f = Pt_coord_parent.y - originalMatrix.b * Pt_coord_element.x - originalMatrix.d * Pt_coord_element.y;

    // Set the new matrix variables
    setMatrixCoordToElement(element, originalMatrix.a, originalMatrix.b, originalMatrix.c, originalMatrix.d, originalMatrix.e, originalMatrix.f);
};

//______________________________________________________________________________________________________________________
export let rotozoom =   ( element           : HTMLElement
                        , originalMatrix    : SVGMatrix
                        , Pt1_coord_element : SVGPoint
                        , Pt1_coord_parent  : SVGPoint
                        , Pt2_coord_element : SVGPoint
                        , Pt2_coord_parent  : SVGPoint
                        ) => {
    // Define useful update variables
    let dxP  = Pt2_coord_element.x - Pt1_coord_element.x;
    let dyP  = Pt2_coord_element.y - Pt1_coord_element.y;
    let dxPrim = Pt2_coord_parent.x - Pt1_coord_parent.x;
    let dyPrim = Pt2_coord_parent.y - Pt1_coord_parent.y;

    // Define the unknown variables
    let c = 0;
    let s = 0;

    // Calculate c and s depending on dxP and dyP values
    if ( dxP === 0 && dyP === 0 ) {
        // The points merge, we give up.
    } else if ( dxP === 0 && dyP !== 0 ) {
        s = -dxPrim / dyP;
        c = dyPrim / dyP;
    } else if ( dxP !== 0 && dyP === 0 ) {
        s = dyPrim / dxP;
        c = dxPrim / dxP;
    } else if ( dxP !== 0 && dyP !== 0 ) {
        s = ( dyPrim / dyP - dxPrim / dxP ) / ( dyP / dxP + dxP / dyP );
        c = ( dxPrim + s * dyP ) / dxP;
    }

    // Now, as we have c and s, we can calculate variables e and f of the new matrix, after rotozoom
    originalMatrix.e = Pt1_coord_parent.x - c * Pt1_coord_element.x + s * Pt1_coord_element.y;
    originalMatrix.f = Pt1_coord_parent.y - s * Pt1_coord_element.x - c * Pt1_coord_element.y;

    // Set the new matrix variables
    setMatrixCoordToElement(element, c, s, -s, c, originalMatrix.e, originalMatrix.f);
};
